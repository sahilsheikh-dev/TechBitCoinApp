package com.begawosoft.tbcapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.begawosoft.tbcapi.dao.UserDao;
import com.begawosoft.tbcapi.entity.UserTbl;
import com.begawosoft.tbcapi.enums.UserResponseMessages;
import com.begawosoft.tbcapi.utilities.Utilities;

@Service
public class UserService {
	
	@Autowired
	public UserDao userDao;
	
	public boolean existsById(Long id) {
		return userDao.existsById(id);
	}
	
	public List<UserTbl> getAllUsers() {
		ArrayList<UserTbl> users = new ArrayList<>();
		List<UserTbl> allUsers = userDao.findAll();
		for(UserTbl user: allUsers)
			if (user.getIsDeleted() == 0 && user.getIsAdmin() == 0) users.add(user);
		return users;
	}

	public UserTbl login(String email, String password) {
		Long id = Long.MIN_VALUE;
		UserTbl user = new UserTbl();
		if (userDao.existsByEmail(email)) {
			if (userDao.existsByPassword(password)) id = userDao.findByEmail(email).getId();
			else id = Long.parseLong("-2");
		} else id = Long.parseLong("-1");
		
		if (id > 0) {
			user = findById(id);
			user.setOutputCode(UserResponseMessages.LOGIN_SUCCESS.getAuthResponseMessage());
		} else if (id == -1) user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		else if (id == -2) user.setOutputCode(UserResponseMessages.LOGIN_PASSWORD.getAuthResponseMessage());
		else user.setOutputCode(UserResponseMessages.OPERATION_FAILED.getAuthResponseMessage());
		
		return user;
	}
	
	public UserTbl findById(Long id) {
		Optional<UserTbl> optUser =  userDao.findById(id);
		return optUser.get();
	}
	
	public UserTbl findByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	public UserTbl findByPassword(String password) {
		return userDao.findByPassword(password);
	}
	
	public UserTbl findByUserPk(String userId) {
		Optional<UserTbl> optUser =  userDao.findByUserPk(userId);
		return optUser.get();
	}

	public UserTbl register(UserTbl user) {
		if (userDao.existsByEmail(user.getEmail())) {
			user.setOutputCode(UserResponseMessages.USER_ALREADY_AVAILABLE.getAuthResponseMessage());
			return user;
		} else {
			user.setUserPk(Utilities.generateRandomId(30));
			user.setReferCode(Utilities.generateRandomId(10));
			return userDao.save(user);
		}
	}
	
	public UserTbl updateUser(UserTbl user) {
		return userDao.save(user);
	}
	
	public UserTbl softDeleteUser(Long id) {
		UserTbl user = new UserTbl();
		if (existsById(id)) {
			user = findById(id);
			user.setIsDeleted(1);
			userDao.save(user);
			if (user.getIsDeleted() == 1) user.setOutputCode(UserResponseMessages.USER_MOVED_TO_RECYCLEBIN.getAuthResponseMessage());
			else user.setOutputCode(UserResponseMessages.OPERATION_FAILED.getAuthResponseMessage());
		} else user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		return user;
		
	}
	
	public UserTbl recoverUserById(Long id) {
		UserTbl user = new UserTbl();
		if (existsById(id)) {
			user = findById(id);
			user.setIsDeleted(0);
			userDao.save(user);
			if (user.getIsDeleted() == 0) user.setOutputCode(UserResponseMessages.USER_RESTORED.getAuthResponseMessage());
			else user.setOutputCode(UserResponseMessages.OPERATION_FAILED.getAuthResponseMessage());
		} else user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		return user;
	}

	public UserTbl deleteUser(Long id) {
		UserTbl user = new UserTbl();
		if (existsById(id)) {
			user = findById(id);
			userDao.delete(user);
			if (existsById(user.getId())) user.setOutputCode(UserResponseMessages.OPERATION_FAILED.getAuthResponseMessage());
			else {
				user = new UserTbl();
				user.setOutputCode(UserResponseMessages.USER_DELETED.getAuthResponseMessage());
			}
		} else user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		return user;
	}

	public UserTbl getUserById(String userId) {
		UserTbl user = new UserTbl();
		if (userDao.existsByUserPk(userId)) {
			user = findByUserPk(userId);
		} else user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		return user;
	}
	
}
