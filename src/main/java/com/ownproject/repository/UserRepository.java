package com.ownproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ownproject.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	Users findByUsername(String username);
	
	void deleteByUsername(String username);
	
	List<Users> findByRoleId(Long id);
}
