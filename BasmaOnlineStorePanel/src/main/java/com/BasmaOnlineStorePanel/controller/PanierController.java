package com.BasmaOnlineStorePanel.controller;

import java.util.ArrayList;

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

import com.BasmaOnlineStorePanel.beans.LignePanier;
import com.BasmaOnlineStorePanel.beans.Panier;
import com.BasmaOnlineStorePanel.feign.ProduitRestClient;
import com.BasmaOnlineStorePanel.model.Produit;
import com.BasmaOnlineStorePanel.model.Utilisateur;
import com.BasmaOnlineStorePanel.repository.LignePanierRepository;
import com.BasmaOnlineStorePanel.repository.PanierRepository;

@Controller
public class PanierController {
	
	@Autowired
	private PanierRepository panierRepository;
	
	@Autowired
	private ProduitRestClient produitRestClient;
	
	@Autowired
	private LignePanierRepository lignePanierRepository;
	
	@ResponseBody
	@GetMapping("/panier")
	public Panier getUserPanier(Authentication authentication) {
		Utilisateur user = (Utilisateur) authentication.getPrincipal();
		if(!panierRepository.existsByUtilisateurID(user.getId())) {
			panierRepository.save(new Panier(null, 0, new ArrayList<>(), user.getId(), user));
		}
		Panier panier = panierRepository.findPanierByUtilisateurID(user.getId());
		for (LignePanier lignePanier : panier.getLignePanier()) {
			lignePanier.setProduit(produitRestClient.getProduitById(lignePanier.getProduitId()));
		}
		return panier;
	}
	
	@ResponseBody
	@PostMapping("/panier")
	public ResponseEntity<Object> addProduitToPanier(Authentication authentication, @RequestBody LignePanier lignePanier) {
		try {
			Utilisateur user = (Utilisateur) authentication.getPrincipal();
			if(!panierRepository.existsByUtilisateurID(user.getId())) {
				panierRepository.save(new Panier(null, 0, new ArrayList<>(), user.getId(), user));
			}
			Panier panier = panierRepository.findPanierByUtilisateurID(user.getId());
			if(lignePanierRepository.existsByProduitIdAndPanier(lignePanier.getProduitId(), panier)) {
				return new ResponseEntity<>("le produit est déja exist dans votre panier", HttpStatus.NOT_ACCEPTABLE);
			}
			Produit produit = produitRestClient.getProduitById(lignePanier.getProduitId());
			lignePanier.setPrice((produit.getPrice() * lignePanier.getQuantity()));
			lignePanier.setPanier(panier);
			lignePanier.setProduit(produit);
			
			panier.setPrice(panier.getPrice() + lignePanier.getPrice());
			panierRepository.save(panier);
			
			lignePanierRepository.save(lignePanier);
			return new ResponseEntity<>("ajouter de produit dans votre panier est terminer avec succes", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@ResponseBody
	@DeleteMapping("/panier")
	public ResponseEntity<Object> deleteProduitToPanier(Authentication authentication, @RequestBody LignePanier lignePanier) {
		try {
			Utilisateur user = (Utilisateur) authentication.getPrincipal();
			Panier panier = panierRepository.findPanierByUtilisateurID(user.getId());
			if(!lignePanierRepository.existsByProduitIdAndPanier(lignePanier.getProduitId(), panier)) {
				return new ResponseEntity<>("le produit est ne exist pas dans votre panier", HttpStatus.NOT_ACCEPTABLE);
			}
			lignePanier = lignePanierRepository.findByProduitIdAndPanier(lignePanier.getProduitId(), panier);
			
			panier.setPrice(panier.getPrice() - lignePanier.getPrice());
			panierRepository.save(panier);
			
			lignePanier.setPanier(null);
			
			lignePanierRepository.delete(lignePanier);
			return new ResponseEntity<>("supprimer le produit de votre panier est terminer avec succes", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@ResponseBody
	@PutMapping("/panier")
	public ResponseEntity<Object> updateQuantityProduit(Authentication authentication, @RequestBody LignePanier lignePanier) {
		try {
			Utilisateur user = (Utilisateur) authentication.getPrincipal();
			Panier panier = panierRepository.findPanierByUtilisateurID(user.getId());
			if(!lignePanierRepository.existsByProduitIdAndPanier(lignePanier.getProduitId(), panier)) {
				return new ResponseEntity<>("le produit est ne exist pas dans votre panier", HttpStatus.NOT_ACCEPTABLE);
			}
			LignePanier lignePanierModifier = lignePanierRepository.findByProduitIdAndPanier(lignePanier.getProduitId(), panier);
			lignePanierModifier.setQuantity(lignePanier.getQuantity());
			Produit produit = produitRestClient.getProduitById(lignePanier.getProduitId());
			panier.setPrice(panier.getPrice() - lignePanierModifier.getPrice());
			lignePanierModifier.setPrice((produit.getPrice() * lignePanier.getQuantity()));
			panier.setPrice(panier.getPrice() + lignePanierModifier.getPrice());
			panierRepository.save(panier);
			
			lignePanierRepository.save(lignePanierModifier);
			return new ResponseEntity<>("La quantité de ce produit dans votre panier a été modifiée avec succès", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
		}
		
	}

}
