package com.BasmaOnlineStore.controller;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BasmaOnlineStore.beans.Utilisateur;
import com.BasmaOnlineStore.repository.UtilisateurRepository;

@org.springframework.stereotype.Controller
public class Controller {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@RequestMapping("/")
    public String home(){
        return "Hello World!";
    }
	
	@ResponseBody
	@GetMapping("/profile")
	public Utilisateur getProfile(Authentication authentication) {
		return utilisateurRepository.findByUsername(authentication.getName());
	}
	
	@ResponseBody
	@PutMapping("/profile")
	public String updateProfie(Authentication authentication, @RequestBody Utilisateur user) {
		Utilisateur user1 = utilisateurRepository.findByUsername(authentication.getName());
		if(user1 != null) {
			try {
				user1.setFirstName(user.getFirstName());
				user1.setLastName(user.getLastName());
				user1.setPassword(passwordEncoder.encode(user.getPassword()));
				utilisateurRepository.save(user1);
				return "modification terminer avec succés";
			} catch (Exception e) {
				return "e";
			}
		} else {
			return "utilisateur n'exist pas";
		}
	}
	

	@ResponseBody
	@PatchMapping(value = "/activeUser") 
	public ResponseEntity<Object> changeactiveUser(@RequestBody Utilisateur user) {
		if (utilisateurRepository.existsById(user.getId())) {
			try {
				Utilisateur user1 = utilisateurRepository.findById(user.getId()).get();
				user1.setActive(user.isActive());
				utilisateurRepository.save(user1);
				// String active; 
				// active = (user1.isActive()) ? "activé" : "désactivé";
				// HtmlEmail email = new HtmlEmail();

				// email.setHostName("smtp.googlemail.com");
				// email.setSmtpPort(465);
				// email.setAuthenticator(new DefaultAuthenticator("email", "pass"));
				// email.setSSLOnConnect(true);

				// email.setFrom("email");

				// email.setSubject("votre compte basma");

				// email.setHtmlMsg("<html><h2>bonjour : " + user1.getFirstName() +" "+ user1.getLastName() + "</h2>"
				// 		+ "<br/><br/> <p>Votre compte a été " + active + "</p> </html>");

				// email.addTo(user1.getEmail());
				// email.send();
				return new ResponseEntity<>("modification du compte terminée avec succès", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
			}
			
		} else {
			return new ResponseEntity<>("ID d'utilisateur est invalide", HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@PutMapping("/chiffre")
	public ResponseEntity<Object> activateProfile(Authentication authentication, @RequestBody Utilisateur user) {
		Utilisateur user1 = utilisateurRepository.findByUsername(authentication.getName());
		if(user1 != null) {
			try {
				if(user1.getChiffre() == user.getChiffre()) {
					user1.setEtat(true);
					utilisateurRepository.save(user1);
					return new ResponseEntity<>("modification du compte terminée avec succès", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("chiffre et invalide", HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>("ID d'utilisateur est invalide", HttpStatus.NOT_FOUND);
		}
	}
}
