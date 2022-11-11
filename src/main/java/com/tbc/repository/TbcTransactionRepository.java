package com.tbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tbc.model.TbcTransaction;

@Repository
public interface TbcTransactionRepository extends JpaRepository<TbcTransaction,Long>{

}