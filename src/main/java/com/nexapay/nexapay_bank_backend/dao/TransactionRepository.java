package com.nexapay.nexapay_bank_backend.dao;

import com.nexapay.nexapay_bank_backend.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    List<TransactionEntity> findByFromAccountNoOrToAccountNoOrderByDateDesc(String fromAccountNo, String toAccountNo);
}
