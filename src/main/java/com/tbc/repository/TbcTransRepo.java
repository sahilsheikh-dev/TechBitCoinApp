package com.tbc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbc.model.TbcTransaction;

public interface TbcTransRepo extends JpaRepository <TbcTransaction,Long>{

	List<TbcTransaction> findByUserId(long id);
}
