package com.nexapay.nexapay_bank_backend.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexapay.dto.response.BankResponse;
import com.nexapay.helper.BankBranch;
import com.nexapay.model.BankEntity;
import com.nexapay.nexapay_bank_backend.service.BankService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BankDAO {

    private static final Logger logger = LoggerFactory.getLogger(BankDAO.class);

    @Autowired
    BankRepository bankRepository;

    public List<BankEntity> getAllBanks() {
        return bankRepository.findAll();
    }

    public BankEntity getBankById(Integer bankId) {
        logger.info("dao get bank by id");
        return bankRepository.findById(bankId).orElse(null);
    }


    public void addBranchToBank(Integer bankId, BankBranch newBranch) {
        BankEntity bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        if (bank.getBranches() == null) {
            bank.setBranches(new ArrayList<>());
        }

        bank.getBranches().add(newBranch);
        bankRepository.save(bank);
    }

}
