package com.ownproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ownproject.entity.Role;
import com.ownproject.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServices {

	@Autowired
	RoleRepository roleRepo;
	
	@Transactional(rollbackFor = Exception.class)
	public Role saveData(Role role) {
		log.info(role.toString());
		return roleRepo.save(role);
	}
	
	@Transactional(readOnly = true)
	public Role findByName(String name) {
		return roleRepo.findByName(name);
	}
	
	
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return roleRepo.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteById(long id) {
		roleRepo.deleteById(id);
	}
}
