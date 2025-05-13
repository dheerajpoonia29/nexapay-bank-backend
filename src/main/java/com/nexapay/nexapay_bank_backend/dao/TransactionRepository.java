package com.nexapay.nexapay_bank_backend.dao;

import com.nexapay.nexapay_bank_backend.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
}
