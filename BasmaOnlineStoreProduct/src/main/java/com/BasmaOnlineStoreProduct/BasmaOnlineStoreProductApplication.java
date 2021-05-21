package com.BasmaOnlineStoreProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.BasmaOnlineStoreProduct.beans.Categorie;
import com.BasmaOnlineStoreProduct.beans.Produit;

@SpringBootApplication
public class BasmaOnlineStoreProductApplication {
	
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(BasmaOnlineStoreProductApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start() {
		return args -> {
			repositoryRestConfiguration.exposeIdsFor(Produit.class);
			repositoryRestConfiguration.exposeIdsFor(Categorie.class);
		};
	}
}
