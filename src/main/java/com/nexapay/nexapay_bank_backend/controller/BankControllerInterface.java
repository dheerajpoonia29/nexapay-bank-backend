package com.nexapay.nexapay_bank_backend.controller;

import com.nexapay.dto.response.BankResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.helper.BankBranch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BankControllerInterface {
    ResponseEntity<Response<Object>> apiHealth();

    ResponseEntity<Response<List<BankResponse>>> getBanks();

    ResponseEntity<Response<BankResponse>> getBank(@RequestParam Integer bankId);

    ResponseEntity<Response<BankBranch>> getBranch(@RequestParam Integer bankId, @RequestParam String ifscCode);
}
