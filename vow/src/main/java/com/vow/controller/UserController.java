package com.vow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vow.dao.UserRepository;
import com.vow.model.User;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/login")
public class UserController {
	@Autowired
	 private UserRepository userRepository;
	
	@GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

}
