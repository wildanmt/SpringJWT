package com.ownproject.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ownproject.entity.Role;
import com.ownproject.entity.Users;
import com.ownproject.repository.UserRepository;
import com.ownproject.service.UserServices;

@WebMvcTest(UserController.class)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserServices userServices;
	
	@MockBean
	private UserRepository userRepo;
	
	@MockBean
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Test
	void getAllData() throws Exception {
		when(userServices.findAll()).thenReturn(
				Arrays.asList(new Users(1, 1, "wildan", "owadsdawdas", new Role())));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/user")
				.header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFkbWluIl0sImlzcyI6Ii9hcGkvbG9naW4iLCJleHAiOjE2Mjk0MjQzMzd9.xHVEaySHjjZGm9O2EP6XwSWxeb6-BTXtejyeHAXM1Q8")
				.accept(MediaType.APPLICATION_JSON);
		
		 this.mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
	}
}
