package com.nexapay.nexapay_bank_backend.controller;

import com.nexapay.dto.request.TransferRequest;
import com.nexapay.dto.response.Response;
import com.nexapay.dto.response.TransferResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TransferControllerInterface {
    ResponseEntity<Response<Object>> apiHealth();

    ResponseEntity<Response<TransferResponse>> createTransfer(@RequestBody TransferRequest transferRequest);

    ResponseEntity<Response<List<TransferResponse>>> listTransfer(@RequestParam String accountNo);
}
