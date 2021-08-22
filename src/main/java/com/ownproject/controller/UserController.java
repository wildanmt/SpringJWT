package com.ownproject.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ownproject.configuration.ResourceNotFoundException;
import com.ownproject.entity.Role;
import com.ownproject.entity.Users;
import com.ownproject.repository.UserRepository;
import com.ownproject.service.UserServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserServices userServices;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/role/{id}")
	public List<Users> getByRoleId(@PathVariable Long id) {
		return userRepo.findByRoleId(id);
	}
	
	@PostMapping
	public Users saveData(@Valid @RequestBody Users user) {
		log.info(user.toString());
		if (userServices.findByUsername(user.getUsername()) != null) {
			throw new IllegalArgumentException("Data already exists");
		}
		return userServices.saveUser(user);
	}
	
	@GetMapping
	public List<Users> getAllData() {
		return userServices.findAll();
	}
	
	@GetMapping("/{username}")
	public Users getByUsername(@PathVariable String username) {
		return Optional.of(userServices.findByUsername(username)).orElseThrow(() -> new ResourceNotFoundException("Data with username : "+username+" not found"));
	}
	
	@DeleteMapping("/{username}")
	public void deleteByUsername(@PathVariable String username) {
		userServices.deleteUser(username);
	}
	
	@GetMapping("/refresh/token")
	public  void refreshToken(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier jwtVerifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(token);
				Users user = userServices.findByUsername(decodedJWT.getId());
				List<String> roles = Arrays.asList(user.getRole().getName());
				String accessToken = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURI().toString())
						.withClaim("roles", roles)
						.sign(algorithm);
				
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", accessToken);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception e) {
				log.error("Error logging in: {}", e.getMessage());
				response.setHeader("error", e.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		} else {
			throw new RuntimeException("Refresh token is missing");
		}	
	}
}
