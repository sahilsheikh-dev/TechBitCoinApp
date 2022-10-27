package com.begawosoft.tbcapi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.begawosoft.tbcapi.entity.UserTbl;

@Repository
public interface UserDao extends JpaRepository<UserTbl, Long> {
	
	UserTbl findByEmail(String email);
	UserTbl findByPassword(String password);
	Optional<UserTbl> findByUserPk(String userPk);
	boolean existsByEmail(String email);
	boolean existsByPassword(String password);
	boolean existsByUserPk(String userk);

}
