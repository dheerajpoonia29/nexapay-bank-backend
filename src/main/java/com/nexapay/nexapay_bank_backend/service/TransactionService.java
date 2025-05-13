package com.nexapay.nexapay_bank_backend.service;

import com.nexapay.dto.response.Response;
import com.nexapay.nexapay_bank_backend.dao.TransactionDAO;
import com.nexapay.dto.request.TransactionRequest;
import com.nexapay.dto.response.TransactionResponse;
import com.nexapay.nexapay_bank_backend.dao.TransactionRepository;
import com.nexapay.nexapay_bank_backend.helper.TransactionOperationResponse;
import com.nexapay.nexapay_bank_backend.helper.TransactionOperation;
import com.nexapay.nexapay_bank_backend.model.TransactionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.nexapay.nexapay_bank_backend.helper.CreateTransactionResponse.createResponse;

@Service
public class TransactionService implements TransactionServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    TransactionOperation transactionOperation;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Response<TransactionResponse> createTransaction(TransactionRequest transactionRequest) {
        logger.info("create transaction entity");
        TransactionEntity transactionEntity = TransactionEntity.toEntity(transactionRequest);

        logger.info("generate transaction id");
        transactionEntity.setTransactionId(this.transactionOperation.generateTransactionId());

        logger.info("do transaction operation");
        TransactionOperationResponse transactionOperation = this.transactionOperation.doTransactionOperation(transactionEntity);
        transactionEntity.setStatus(transactionOperation.getStatus());
        transactionEntity.setStatusInfo(transactionOperation.getMsg());

        logger.info("persist transaction");
        boolean persistStatus = transactionDAO.saveTransaction(transactionEntity);

        logger.info("create transaction response");
        if(persistStatus) {
            return  createResponse(HttpStatus.CREATED, "transaction created", transactionEntity);
        }
        transactionEntity.setStatus(false);
        transactionEntity.setStatusInfo("internal server error");
        return  createResponse(HttpStatus.INTERNAL_SERVER_ERROR, transactionEntity.getStatusInfo(), null);
    }

    @Override
    public Response<List<TransactionResponse>> getTransactions(String accountNo) {
        logger.info("find transactions");
        List<TransactionEntity> transactions = transactionRepository.findByFromAccountNoOrToAccountNoOrderByDateDesc(accountNo, accountNo);

        logger.info("return response");
        List<TransactionResponse> transactionResponseList = new ArrayList<>();
        for (TransactionEntity transactionEntity: transactions) {
            transactionResponseList.add(TransactionResponse.builder()
                    .transactionId(transactionEntity.getTransactionId())
                    .fromAccountNo(transactionEntity.getFromAccountNo())
                    .toAccountNo(transactionEntity.getToAccountNo())
                    .amount(transactionEntity.getAmount())
                    .date(transactionEntity.getDate())
                    .status(transactionEntity.getStatus())
                    .statusInfo(transactionEntity.getStatusInfo()).build());
        }
        return Response.<List<TransactionResponse>>builder()
                .responseStatus(HttpStatus.OK)
                .responseStatusInt(HttpStatus.OK.value())
                .responseMsg("transactions found")
                .responseData(transactionResponseList).build();
    }
}
