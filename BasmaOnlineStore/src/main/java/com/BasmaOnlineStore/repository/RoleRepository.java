package com.BasmaOnlineStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BasmaOnlineStore.beans.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Role findByName(String name);

}
