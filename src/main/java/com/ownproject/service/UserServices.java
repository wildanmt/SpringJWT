package com.ownproject.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ownproject.entity.Users;
import com.ownproject.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServices implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Users findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Users saveUser(Users user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		log.info(user.toString());		
		return userRepo.save(user);
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Users user = userRepo.findByUsername(username);
		if (user == null) {
			log.error("User not found");
			throw new UsernameNotFoundException(username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}
	
	@Transactional(readOnly = true)
	public List<Users> findAll() {
		return userRepo.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteUser(String username) {		
		userRepo.deleteByUsername(username);
	}
}
