package com.BasmaOnlineStore.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.BasmaOnlineStore.beans.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	public Utilisateur findByUsername(String username);
	
}
