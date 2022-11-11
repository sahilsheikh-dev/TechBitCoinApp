package com.tbc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tbc.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByVerificationCode(String verificationCode);

	List<User> findByEnabled(boolean b);

	User findByMail(String name);

	
	
}