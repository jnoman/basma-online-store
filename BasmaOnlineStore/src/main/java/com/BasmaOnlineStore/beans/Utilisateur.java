package com.BasmaOnlineStore.beans;


import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
    private String firstName;
	@NotNull
    private String lastName;
	@Column(unique = true)
	@NotNull
    private String username;
	@Email
	@Column(unique = true)
	@NotNull
    private String email;
	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private boolean active;
	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private boolean etat;
	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int chiffre;
	@ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles=new ArrayList<>();
	
}
