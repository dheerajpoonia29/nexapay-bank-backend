package com.nexapay.nexapay_bank_backend.dao;

import com.nexapay.nexapay_bank_backend.model.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionDAO {
    @Autowired
    TransactionRepository transactionRepository;

    public boolean saveTransaction(TransactionEntity transactionEntity) {
        transactionRepository.save(transactionEntity);
        return true;
    }
}
