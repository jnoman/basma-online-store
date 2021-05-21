package com.BasmaOnlineStore.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BasmaOnlineStore.beans.Utilisateur;
import com.BasmaOnlineStore.service.AccountService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AccountService accountService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur user = accountService.loadUserByUsername(username);
		if(user == null) throw new RuntimeException("utilisateur invalide");
		if(user.isActive() != true) throw new RuntimeException("account disable");
		Collection<GrantedAuthority> authorities= new ArrayList<>();
		user.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        });
		return new User(user.getUsername(), user.getPassword(), authorities);
	}
	


}
