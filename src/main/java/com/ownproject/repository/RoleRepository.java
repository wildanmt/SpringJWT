package com.ownproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ownproject.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
	
	void deleteByName(String name);
}
