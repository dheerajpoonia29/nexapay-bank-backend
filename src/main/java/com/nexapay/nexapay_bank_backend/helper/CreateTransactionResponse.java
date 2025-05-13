package com.nexapay.nexapay_bank_backend.helper;

import com.nexapay.dto.response.Response;
import com.nexapay.dto.response.TransactionResponse;
import com.nexapay.nexapay_bank_backend.model.TransactionEntity;
import org.springframework.http.HttpStatus;

public class CreateTransactionResponse {
    public static Response<TransactionResponse> createResponse(HttpStatus httpStatus, String responseMsg,
                                                               TransactionEntity transactionEntity) {
        return Response.<TransactionResponse>builder()
                .responseStatus(httpStatus)
                .responseStatusInt(HttpStatus.CREATED.value())
                .responseMsg(responseMsg)
                .responseData(TransactionResponse.builder()
                        .transactionId(transactionEntity.getTransactionId())
                        .fromAccountNo(transactionEntity.getFromAccountNo())
                        .toAccountNo(transactionEntity.getToAccountNo())
                        .amount(transactionEntity.getAmount())
                        .date(transactionEntity.getDate())
                        .status(transactionEntity.getStatus())
                        .statusInfo(transactionEntity.getStatusInfo()).build()).build();
    }
}
