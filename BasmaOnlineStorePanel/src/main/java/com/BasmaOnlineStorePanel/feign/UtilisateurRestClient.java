package com.BasmaOnlineStorePanel.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.BasmaOnlineStorePanel.model.Utilisateur;

@FeignClient(name="BasmaOnlineStore", url = "localhost:8080")
public interface UtilisateurRestClient {
	@GetMapping(path = "/utilisateurs/{id}")
	Utilisateur getUtilisateurById(@PathVariable Long id);
}
