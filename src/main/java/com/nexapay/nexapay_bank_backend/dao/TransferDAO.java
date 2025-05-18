package com.nexapay.nexapay_bank_backend.dao;

import com.nexapay.model.TransferEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class TransferDAO {
    private static final Logger logger = LoggerFactory.getLogger(TransferDAO.class);

    @Autowired
    TransferRepository transferRepository;

    public boolean saveTransfer(TransferEntity transferEntity) {
        logger.info("transfer dao saving entity");
        transferEntity.setDate(new Timestamp(System.currentTimeMillis()));
        transferRepository.save(transferEntity);
        return true;
    }

    public List<TransferEntity> getTransfers(String transferNo, String accountNo) {
        logger.info("transfer dao getting transfers list");
        return transferRepository.findByFromAccountNoOrToAccountNoOrderByDateDesc(accountNo, accountNo);
    };
}
