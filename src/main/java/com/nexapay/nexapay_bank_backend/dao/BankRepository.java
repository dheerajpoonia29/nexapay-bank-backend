package com.nexapay.nexapay_bank_backend.dao;

import com.nexapay.model.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Integer> {
}
