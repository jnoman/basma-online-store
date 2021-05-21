package com.BasmaOnlineStorePanel.model;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.BasmaOnlineStorePanel.beans.Panier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PanierId implements Serializable{
	@ManyToOne
	@JoinColumn(name="panier_id")
	private Panier panier;
	@ManyToOne
	@JoinColumn(name="produit_id")
	private Produit produit;
}
