package com.BasmaOnlineStorePanel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BasmaOnlineStorePanel.beans.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
	public boolean existsByUtilisateurIDAndId(Long utilisateurID, Long id);
	public boolean existsByUtilisateurIDAndIdAndEtat(Long utilisateurID, Long id, Boolean etat);
	public List<Commande> findCommandeByUtilisateurID(Long utilisateurID);
	public List<Commande> findCommandeByEtat(Boolean etat);
}
