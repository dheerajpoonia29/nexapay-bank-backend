package com.nexapay.nexapay_bank_backend.controller;

import com.nexapay.dto.request.TransactionRequest;
import com.nexapay.dto.response.TransactionResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.nexapay_bank_backend.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
@CrossOrigin
public class TransactionController implements TransactionControllerInterface {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;

    @Override
    @GetMapping("/health")
    public ResponseEntity<Response<Object>> apiHealth() {
        logger.info("account api");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.builder()
                        .responseStatus(HttpStatus.OK)
                        .responseStatusInt(HttpStatus.OK.value())
                        .responseMsg("bank api is up!!")
                        .responseData(null).build());
    }

    @Override
    @PostMapping("/transaction")
    public ResponseEntity<Response<TransactionResponse>> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        logger.info("create transaction");
        Response<TransactionResponse> response = transactionService.createTransaction(transactionRequest);
        logger.info("send response");
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }
}
