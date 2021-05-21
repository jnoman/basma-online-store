package com.BasmaOnlineStorePanel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BasmaOnlineStorePanel.beans.LignePanier;
import com.BasmaOnlineStorePanel.beans.Panier;

public interface LignePanierRepository extends JpaRepository<LignePanier, Long> {
	public boolean existsByProduitIdAndPanier(Long produitId, Panier panier);
	public LignePanier findByProduitIdAndPanier(Long produitId, Panier panier);
}
