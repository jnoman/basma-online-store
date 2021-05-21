package com.BasmaOnlineStorePanel.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.BasmaOnlineStorePanel.model.Utilisateur;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Commande {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull @Column(unique = true)
	private Date dateCommande;
	private Boolean etat;
	@NotNull
	private double price;
	@OneToMany(mappedBy = "commande", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<LigneCommande> ligneCommande;
	@NotNull
	private Long utilisateurID;
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Utilisateur utilisateur;
	@ManyToOne
	private Coupon coupon;
	@Override
	public String toString() {
		return "Commande [id=" + id + ", dateCommande=" + dateCommande + ", etat=" + etat + ", price=" + price
				+ ", utilisateurID=" + utilisateurID + "]";
	}
	
	
}
