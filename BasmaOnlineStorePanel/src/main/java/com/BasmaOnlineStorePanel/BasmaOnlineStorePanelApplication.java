package com.BasmaOnlineStorePanel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.BasmaOnlineStorePanel.beans.Panier;
import com.BasmaOnlineStorePanel.feign.ProduitRestClient;
import com.BasmaOnlineStorePanel.model.Produit;
import com.BasmaOnlineStorePanel.repository.PanierRepository;

@EnableFeignClients
@SpringBootApplication
public class BasmaOnlineStorePanelApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasmaOnlineStorePanelApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(ProduitRestClient produitRestClient, PanierRepository panierRepository) {
		return args -> {
//			Produit p1 = produitRestClient.getProduitById(2);
//			System.out.println(p1.toString());
//			Panier panier = panierRepository.findById(1L).get();
//			System.out.println(panierRepository.existsById(1L));
		};
	}
}
