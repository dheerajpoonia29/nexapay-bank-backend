package com.nexapay.nexapay_bank_backend.helper;

import com.nexapay.dto.request.AccountRequest;
import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.response.TransactionResponse;
import com.nexapay.nexapay_bank_backend.client.AccountServiceClient;
import com.nexapay.nexapay_bank_backend.model.TransactionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionOperation {
    private static final Logger logger = LoggerFactory.getLogger(TransactionOperation.class);

    @Autowired
    AccountServiceClient accountServiceClient;

    public TransactionOperationResponse doTransactionOperation(TransactionEntity transactionEntity) {
        logger.info("get account detail");
        AccountResponse senderAccount = accountServiceClient.getAccount(transactionEntity.getFromAccountNo());
        AccountResponse receiverAccount = accountServiceClient.getAccount(transactionEntity.getToAccountNo());

        logger.info("validate receiver account no");
        if (senderAccount==null)
            return TransactionOperationResponse.builder().status(false).msg("sender account no found").build();
        if(receiverAccount==null)
           return TransactionOperationResponse.builder().status(false).msg("receiver account no found").build();

        logger.info("check available balance in sender account");
        if(senderAccount.getBalance()<transactionEntity.getAmount())
            return TransactionOperationResponse.builder().status(false).msg("sender have insufficient balance").build();

        logger.info("deduct amount from sender and add in receiver account");
        AccountRequest senderAccountRequest = AccountRequest.builder()
                .accountNo(senderAccount.getAccountNo())
                .balance(senderAccount.getBalance() - transactionEntity.getAmount())
                .build();
        AccountRequest receiverAccountRequest = AccountRequest.builder()
                .accountNo(receiverAccount.getAccountNo())
                .balance(receiverAccount.getBalance() + transactionEntity.getAmount())
                .build();

        logger.info("persist updated account");
        accountServiceClient.updateAccount(senderAccountRequest);
        accountServiceClient.updateAccount(receiverAccountRequest);

        logger.info("return response");
        return TransactionOperationResponse.builder().status(true).msg("transaction is success").build();
    }

    public long generateTransactionId() {
        long timestamp = System.currentTimeMillis(); // 13-digit
        long randomSuffix = (long)(Math.random() * 9000) + 1000; // 4-digit random
        return (timestamp / 1000) * 10000 + randomSuffix; // trims to 10-11 digits
    }
}
