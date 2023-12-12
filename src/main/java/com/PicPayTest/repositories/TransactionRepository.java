package com.PicPayTest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PicPayTest.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
}
