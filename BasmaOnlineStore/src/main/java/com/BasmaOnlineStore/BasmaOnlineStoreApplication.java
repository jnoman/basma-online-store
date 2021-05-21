package com.BasmaOnlineStore;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.BasmaOnlineStore.beans.Role;
import com.BasmaOnlineStore.beans.Utilisateur;
import com.BasmaOnlineStore.service.AccountService;
import com.BasmaOnlineStore.service.impl.AccountServiceImpl;

@SpringBootApplication
public class BasmaOnlineStoreApplication {


	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;


	public static void main(String[] args) {
		SpringApplication.run(BasmaOnlineStoreApplication.class, args);
	}



	@Bean
	CommandLineRunner start(AccountService accountService) {
		return args -> {
			repositoryRestConfiguration.exposeIdsFor(Utilisateur.class);
//			Role r1 = accountService.saveRole(new Role(null, "ADMIN"));
//			accountService.saveRole(new Role(null, "USER"));
//			Utilisateur admin = accountService.saveUser(new Utilisateur(null, "admin", "admin", "admin@gmail.com", 
//					"admin@gmail.com", "aaaaaaaa", true,false, 0,new ArrayList<>()));
//			accountService.saveUser(new Utilisateur(null, "jamal eddine", "noman", "jamalnoman62@gmail.com", 
//					"jamalnoman62@gmail.com", "aaaaaaaa", false, false, 0,new ArrayList<>()));
//			accountService.addRoleToUser(admin, r1);
		};
	}

	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
	

}
