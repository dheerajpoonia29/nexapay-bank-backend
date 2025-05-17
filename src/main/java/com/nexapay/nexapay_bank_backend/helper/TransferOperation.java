package com.nexapay.nexapay_bank_backend.helper;

import com.nexapay.dto.request.AccountRequest;
import com.nexapay.dto.response.AccountResponse;
import com.nexapay.nexapay_bank_backend.client.AccountClient;
import com.nexapay.model.TransferEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferOperation {
    private static final Logger logger = LoggerFactory.getLogger(TransferOperation.class);

    @Autowired
    AccountClient accountClient;

    public TransferOperationResponse doTransferOperation(TransferEntity transferEntity) {
        logger.info("get account detail");
        AccountResponse senderAccount = accountClient.getAccount(transferEntity.getFromAccountNo());
        AccountResponse receiverAccount = accountClient.getAccount(transferEntity.getToAccountNo());

        logger.info("validate receiver account no");
        if (senderAccount==null)
            return TransferOperationResponse.builder().status(false).msg("sender account not found").build();
        if(receiverAccount==null)
           return TransferOperationResponse.builder().status(false).msg("receiver account not found").build();

        logger.info("check available balance in sender account");
        if(senderAccount.getBalance()< transferEntity.getAmount())
            return TransferOperationResponse.builder().status(false).msg("sender have insufficient balance").build();

        logger.info("deduct amount from sender and add in receiver account");
        AccountRequest senderAccountRequest = AccountRequest.builder()
                .accountNo(senderAccount.getAccountNo())
                .balance(senderAccount.getBalance() - transferEntity.getAmount())
                .build();
        AccountRequest receiverAccountRequest = AccountRequest.builder()
                .accountNo(receiverAccount.getAccountNo())
                .balance(receiverAccount.getBalance() + transferEntity.getAmount())
                .build();

        logger.info("persist updated account");
        accountClient.updateAccount(senderAccountRequest);
        accountClient.updateAccount(receiverAccountRequest);

        logger.info("return response");
        return TransferOperationResponse.builder().status(true).msg("transfer is success").build();
    }

    public long generateTransferId() {
        long timestamp = System.currentTimeMillis(); // 13-digit
        long randomSuffix = (long)(Math.random() * 9000) + 1000; // 4-digit random
        return (timestamp / 1000) * 10000 + randomSuffix; // trims to 10-11 digits
    }
}
