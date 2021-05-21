package com.BasmaOnlineStorePanel.model;

import lombok.Data;

@Data
public class Utilisateur {
	private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

}
