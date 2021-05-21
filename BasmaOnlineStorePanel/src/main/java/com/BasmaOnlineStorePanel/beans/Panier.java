package com.BasmaOnlineStorePanel.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Panier {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private double price;
	@OneToMany(mappedBy = "panier")
	private List<LignePanier> lignePanier;
	@NotNull
	private Long utilisateurID;
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Utilisateur utilisateur;
	@Override
	public String toString() {
		return "Panier [id=" + id + ", price=" + price + ", utilisateurID=" + utilisateurID + "]";
	}
	
	
}
