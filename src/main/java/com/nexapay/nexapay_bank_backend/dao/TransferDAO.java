package com.nexapay.nexapay_bank_backend.dao;

import com.nexapay.nexapay_bank_backend.model.TransferEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferDAO {
    @Autowired
    TransferRepository transferRepository;

    public boolean saveTransfer(TransferEntity transferEntity) {
        transferRepository.save(transferEntity);
        return true;
    }
}
