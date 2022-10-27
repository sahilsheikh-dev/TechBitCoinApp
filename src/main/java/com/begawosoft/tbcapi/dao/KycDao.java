package com.begawosoft.tbcapi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.begawosoft.tbcapi.entity.KycTbl;

public interface KycDao extends JpaRepository<KycTbl, Long> {

	Optional<KycTbl> findByUserPk(String userPk);
	boolean existsByUserPk(String userPk);

}
