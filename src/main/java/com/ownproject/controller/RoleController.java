package com.ownproject.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ownproject.configuration.ResourceNotFoundException;
import com.ownproject.entity.Role;
import com.ownproject.service.RoleServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/role")
public class RoleController {
	
	@Autowired
	RoleServices roleServices;
	
	@PostMapping
	public Role saveData(@Valid @RequestBody Role role) {
		log.debug(role.toString());
		if (roleServices.findByName(role.getName()) != null) {
			throw new IllegalArgumentException("Data already exist");
		}
		return roleServices.saveData(role); 
	}
	
	@GetMapping
	public List<Role> getAllData() {
		return roleServices.findAll();
	}
	
	@GetMapping("/{name}")
	public Role getByName(@PathVariable String name) {
		return Optional.of(roleServices.findByName(name)).orElseThrow(() -> new ResourceNotFoundException("Data with username : "+name+" not found"));
	}
	
	@DeleteMapping("/{id}")
	public void deleteByName(@PathVariable long id) {
		roleServices.deleteById(id);
	}
}
