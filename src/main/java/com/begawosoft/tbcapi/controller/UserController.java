package com.begawosoft.tbcapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.begawosoft.tbcapi.entity.UserTbl;
import com.begawosoft.tbcapi.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<UserTbl> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/users/{userId}")
	public UserTbl getUserByUserId(@PathVariable("userId") String userId) {
		return userService.getUserById(userId);
	}
	
	@GetMapping("/login")
	public UserTbl login(@RequestParam("email") String email, @RequestParam("password") String password) {
		return userService.login(email, password);
	}
	
	@PostMapping("/register")
	public UserTbl register(@RequestBody UserTbl user) {
		return userService.register(user);
	}
	
	@PutMapping("/updateUser")
	public UserTbl updateUser(@RequestBody UserTbl user) {
		return userService.updateUser(user);
	}
	
	@DeleteMapping("/softDeleteUser/{id}")
	public UserTbl softDeleteUser(@PathVariable("id") Long id) {
		return userService.softDeleteUser(id);
	}
	
	@PutMapping("/recoverUser/{id}")
	public UserTbl recoverUserById(@PathVariable("id") Long id) {
		return userService.recoverUserById(id);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public UserTbl deleteUser(@PathVariable("id") Long id) {
		return userService.deleteUser(id);
	}
	
}
