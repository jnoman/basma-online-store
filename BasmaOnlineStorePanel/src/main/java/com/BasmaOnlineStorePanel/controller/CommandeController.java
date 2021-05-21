package com.BasmaOnlineStorePanel.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BasmaOnlineStorePanel.beans.Commande;
import com.BasmaOnlineStorePanel.beans.Coupon;
import com.BasmaOnlineStorePanel.beans.LigneCommande;
import com.BasmaOnlineStorePanel.beans.LignePanier;
import com.BasmaOnlineStorePanel.beans.Panier;
import com.BasmaOnlineStorePanel.feign.ProduitRestClient;
import com.BasmaOnlineStorePanel.feign.UtilisateurRestClient;
import com.BasmaOnlineStorePanel.model.Utilisateur;
import com.BasmaOnlineStorePanel.repository.CommandeRepository;
import com.BasmaOnlineStorePanel.repository.CouponRepository;
import com.BasmaOnlineStorePanel.repository.LigneCommandeRepository;
import com.BasmaOnlineStorePanel.repository.LignePanierRepository;
import com.BasmaOnlineStorePanel.repository.PanierRepository;

@Controller
public class CommandeController {

	@Autowired
	private PanierRepository panierRepository;

	@Autowired
	private CommandeRepository commandeRepository;

	@Autowired
	private ProduitRestClient produitRestClient;

	@Autowired
	private UtilisateurRestClient utilisateurRestClient;

	@Autowired
	private LignePanierRepository lignePanierRepository;

	@Autowired
	private LigneCommandeRepository ligneCommandeRepository;
	
	@Autowired 
	private CouponRepository couponRepository;

	@ResponseBody
	@PostMapping("/commande")
	public ResponseEntity<Object> passeCommande(Authentication authentication, @RequestBody Coupon coupon) {
		try {
			Utilisateur user = (Utilisateur) authentication.getPrincipal();
			Panier panier = panierRepository.findPanierByUtilisateurID(user.getId());
			if (panier.getLignePanier().size() == 0) {
				return new ResponseEntity<>("panier est vide", HttpStatus.BAD_REQUEST);
			}
			if(coupon.getCode() != null) {
				if(!couponRepository.existsByCode(coupon.getCode())) {
					return new ResponseEntity<>("code de coupon est n'exist pas", HttpStatus.NOT_FOUND);
				}
			}
			Commande commande = new Commande(null, new Date(), null, panier.getPrice(), null, panier.getUtilisateurID(), null, null);
			
			if(coupon.getCode() != null) {
				commande.setCoupon(couponRepository.findByCode(coupon.getCode()));
				commande.setPrice(commande.getPrice() * 0.85);
			}
			commande = commandeRepository.save(commande);
			for (LignePanier lignePanier : panier.getLignePanier()) {
				ligneCommandeRepository.save(new LigneCommande(null, lignePanier.getQuantity(), lignePanier.getPrice(),
						lignePanier.getProduitId(), commande, null));
				lignePanier.setPanier(null);
				lignePanierRepository.delete(lignePanier);
			}
			panier.setPrice(0);
			panier.setLignePanier(null);
			panierRepository.save(panier);
			return new ResponseEntity<>("Votre commande a réussi", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@GetMapping("/commande")
	public List<Commande> getUserCommande(Authentication authentication) {
		Utilisateur user = (Utilisateur) authentication.getPrincipal();
		List<Commande> commandes = commandeRepository.findCommandeByUtilisateurID(user.getId());
		for (Commande commande : commandes) {
			for (LigneCommande ligneCommande : commande.getLigneCommande()) {
				ligneCommande.setProduit(produitRestClient.getProduitById(ligneCommande.getProduitId()));
			}
		}
		return commandes;
	}

	@ResponseBody
	@DeleteMapping("commande")
	public ResponseEntity<Object> deleteCommandeUser(Authentication authentication, @RequestBody Commande commande) {
		Utilisateur user = (Utilisateur) authentication.getPrincipal();
		if (!commandeRepository.existsByUtilisateurIDAndId(user.getId(), commande.getId())) {
			return new ResponseEntity<>("Vous n'avez pas de commande avec cet identifiant", HttpStatus.NOT_ACCEPTABLE);
		}
		if (!commandeRepository.existsByUtilisateurIDAndIdAndEtat(user.getId(), commande.getId(), null)) {
			return new ResponseEntity<>("Vous n'avez pas le droit de supprimer cette demande",
					HttpStatus.NOT_ACCEPTABLE);
		}
		Commande commandeSupprime = commandeRepository.findById(commande.getId()).get();
		commandeRepository.delete(commandeSupprime);
		return new ResponseEntity<>("supprimer la commande est terminer avec succes", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping("/allCommande")
	public List<Commande> getAllCommande() {
		List<Commande> commandes = commandeRepository.findCommandeByEtat(null);
		for (Commande commande : commandes) {
			for (LigneCommande ligneCommande : commande.getLigneCommande()) {
				ligneCommande.setProduit(produitRestClient.getProduitById(ligneCommande.getProduitId()));
			}
		}
		return commandes;
	}

	@ResponseBody
	@PutMapping("/etatCommande")
	public ResponseEntity<Object> etatCommande(@RequestBody Commande commande) {
		// try {
			if (!commandeRepository.existsById(commande.getId())) {
				return new ResponseEntity<>("Vous n'avez pas de commande avec cet identifiant",
						HttpStatus.NOT_ACCEPTABLE);
			}
			Commande commandeModifier = commandeRepository.findById(commande.getId()).get();
			System.out.println(commandeModifier.toString());
			commandeModifier.setEtat(commande.getEtat());
			commandeRepository.save(commandeModifier);

			Utilisateur user = utilisateurRestClient.getUtilisateurById(commandeModifier.getId());
			// String active;
			// active = (commandeModifier.getEtat()) ? "acceptée" : "efusée";
			// HtmlEmail email = new HtmlEmail();

			// email.setHostName("smtp.googlemail.com");
			// email.setSmtpPort(465);
			// email.setAuthenticator(new DefaultAuthenticator("email", "pass"));
			// email.setSSLOnConnect(true);

			// email.setFrom("email");

			// email.setSubject("votre commande basma");

			// email.setHtmlMsg("<html><h2>bonjour : " + user.getFirstName() + " " + user.getLastName() + "</h2>"
			// 		+ "<br/><br/> <p>Votre commande a été " + active + "</p> </html>");

			// email.addTo(user.getEmail());
			// email.send();

			return new ResponseEntity<>("L'état de cette commande a été modifiée avec succès", HttpStatus.OK);
		// } catch (EmailException e) {
		// 	return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
		// }
	}

}
