package com.nexapay.nexapay_bank_backend.dao;

import com.nexapay.model.TransferEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class TransferDAO {
    @Autowired
    TransferRepository transferRepository;

    public boolean saveTransfer(TransferEntity transferEntity) {
        transferEntity.setDate(new Timestamp(System.currentTimeMillis()));
        transferRepository.save(transferEntity);
        return true;
    }
}
