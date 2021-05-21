package com.BasmaOnlineStore.controller;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BasmaOnlineStore.beans.Utilisateur;
import com.BasmaOnlineStore.service.AccountService;

@RepositoryRestController
public class UserController {
	
	@Autowired 
	private AccountService accountService;

	@ResponseBody
	@RequestMapping(path="/utilisateurs", method=RequestMethod.POST)
	public ResponseEntity<Object> saveUser(@RequestBody Utilisateur user) {
		try {
			user.setEtat(false);
			int chiffre =  (int) (Math.random()*1000000);
			user.setChiffre(chiffre);
			accountService.saveUser(user);
			
			// HtmlEmail email = new HtmlEmail();

			// email.setHostName("smtp.googlemail.com");
			// email.setSmtpPort(465);
			// email.setAuthenticator(new DefaultAuthenticator("email", "pass"));
			// email.setSSLOnConnect(true);

			// email.setFrom("email");

			// email.setSubject("votre compte basma");

			// email.setHtmlMsg("<html><h2>bonjour : " + user.getFirstName() +" "+ user.getLastName() + "</h2>"
			// 		+ "<br/><br/> <p>Votre chiffre de confirmation est : " + chiffre + "</p> </html>");

			// email.addTo(user.getEmail());
			// email.send();

			return new ResponseEntity<>("ajouter du compte terminée avec succès", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
		}
		
	}
	
}
