package com.BasmaOnlineStore.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BasmaOnlineStore.beans.Role;
import com.BasmaOnlineStore.beans.Utilisateur;
import com.BasmaOnlineStore.repository.RoleRepository;
import com.BasmaOnlineStore.repository.UtilisateurRepository;
import com.BasmaOnlineStore.service.AccountService;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {
	
	@Autowired 
	private UtilisateurRepository utilisateurRepository;
	
	@Autowired 
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Utilisateur saveUser(Utilisateur utilisateur) {
		Utilisateur user = utilisateurRepository.findByUsername(utilisateur.getUsername());
		if(user != null) throw new RuntimeException("utilisateur exist déja");
		utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
		Role role = roleRepository.findByName("USER");
		utilisateur.getRoles().add(role);
		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Utilisateur loadUserByUsername(String username) {
		return utilisateurRepository.findByUsername(username);
	}

	@Override
	public void addRoleToUser(Utilisateur utilisateur,Role role) {
		utilisateur.getRoles().add(role);
		utilisateurRepository.save(utilisateur);
	}

}
