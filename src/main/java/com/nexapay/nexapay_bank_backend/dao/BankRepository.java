package com.nexapay.nexapay_bank_backend.dao;

import com.nexapay.helper.BankBranch;
import com.nexapay.model.AccountEntity;
import com.nexapay.model.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Integer> {
}
