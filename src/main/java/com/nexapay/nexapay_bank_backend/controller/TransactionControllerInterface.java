package com.nexapay.nexapay_bank_backend.controller;

import com.nexapay.dto.response.Response;
import com.nexapay.dto.request.TransactionRequest;
import com.nexapay.dto.response.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface TransactionControllerInterface {
    ResponseEntity<Response<Object>> apiHealth();

    ResponseEntity<Response<TransactionResponse>> createTransaction(@RequestBody TransactionRequest transactionRequest);
}
