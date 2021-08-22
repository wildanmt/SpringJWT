package com.ownproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ownproject.entity.Role;
import com.ownproject.entity.Users;
import com.ownproject.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServices {
	@InjectMocks
	private com.ownproject.service.UserServices userServices;
	
	@Mock
	private UserRepository userRepo;
	
	@Test
	public void userServiceTest() {	
		when(userRepo.findAll()).thenReturn(
				Arrays.asList(new Users(1, 1, "wildan", "owadsdawdas", new Role())));
	
		List<Users> users = userServices.findAll();
		assertEquals(1,  users.get(0).getId());
	}
}
