package com.BasmaOnlineStorePanel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.BasmaOnlineStorePanel.beans.Panier;

public interface PanierRepository extends JpaRepository<Panier, Long> {
	public Panier findPanierByUtilisateurID(Long utilisateurID);
	public boolean existsByUtilisateurID(Long utilisateurID);
}
