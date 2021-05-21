package com.BasmaOnlineStorePanel.model;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.BasmaOnlineStorePanel.beans.Commande;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeId implements Serializable{
	@ManyToOne
	@JoinColumn(name="commande_id")
	private Commande commande;
	@ManyToOne
	@JoinColumn(name="produit_id")
	private Produit produit;
}