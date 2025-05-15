package com.nexapay.nexapay_bank_backend.dao;

import com.nexapay.model.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Integer> {
    List<TransferEntity> findByFromAccountNoOrToAccountNoOrderByDateDesc(String fromAccountNo, String toAccountNo);
}
