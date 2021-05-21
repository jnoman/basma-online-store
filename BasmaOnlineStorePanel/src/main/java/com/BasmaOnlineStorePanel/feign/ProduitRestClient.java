package com.BasmaOnlineStorePanel.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.BasmaOnlineStorePanel.model.Produit;

@FeignClient(name="BasmaOnlineStoreProduct", url = "localhost:8080")
public interface ProduitRestClient {
	
	@GetMapping(path = "/produits/{id}")
	Produit getProduitById(@PathVariable Long id);

}
