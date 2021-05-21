package com.BasmaOnlineStore.service;

import com.BasmaOnlineStore.beans.Role;
import com.BasmaOnlineStore.beans.Utilisateur;

public interface AccountService {
	public Utilisateur saveUser(Utilisateur utilisateur);
	public Role saveRole(Role role);
	public Utilisateur loadUserByUsername(String username);
    public void addRoleToUser(Utilisateur utilisateur,Role role);
}

