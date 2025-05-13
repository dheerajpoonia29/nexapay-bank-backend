package com.nexapay.nexapay_bank_backend.service;

import com.nexapay.dto.request.TransactionRequest;
import com.nexapay.dto.response.Response;
import com.nexapay.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionServiceInterface {
    Response<TransactionResponse> createTransaction(TransactionRequest transactionRequest);

    Response<List<TransactionResponse>> getTransactions(String accountNo);
}
