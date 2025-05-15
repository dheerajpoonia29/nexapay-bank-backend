package com.nexapay.nexapay_bank_backend.controller;

import com.nexapay.dto.response.BankResponse;
import com.nexapay.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BankControllerInterface {
    ResponseEntity<Response<Object>> apiHealth();

    ResponseEntity<Response<List<BankResponse>>> getBanks();

    public ResponseEntity<Response<BankResponse>> getBank(@RequestParam Integer bankId);
}
