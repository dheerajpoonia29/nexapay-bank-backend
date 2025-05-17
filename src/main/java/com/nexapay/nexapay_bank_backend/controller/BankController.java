package com.nexapay.nexapay_bank_backend.controller;

import com.nexapay.dto.response.BankResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.nexapay_bank_backend.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
@CrossOrigin
public class BankController implements BankControllerInterface {
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);

    @Autowired
    BankService bankService;

    @Override
    @GetMapping("/health")
    public ResponseEntity<Response<Object>> apiHealth() {
        logger.info("bank health api");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.builder()
                        .responseStatus(HttpStatus.OK)
                        .responseStatusInt(HttpStatus.OK.value())
                        .responseMsg("bank transaction api is up!!")
                        .responseData(null).build());
    }

    @Override
    @GetMapping("/get-all")
    public ResponseEntity<Response<List<BankResponse>>> getBanks() {
        logger.info("get all bank");
        Response<List<BankResponse>> response = bankService.fetchAllBanks();
        return ResponseEntity
                .status(response.getResponseStatus())
                .body(response);
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<Response<BankResponse>> getBank(@RequestParam Integer bankId) {
        logger.info("get single bank");
        Response<BankResponse> response = bankService.fetchSingleBank(bankId);
        return ResponseEntity
                .status(response.getResponseStatus())
                .body(response);
    }

    @Override
    @GetMapping("/get-branch")
    public ResponseEntity<Response<Object>> getBranch(@RequestParam Integer bankId, @RequestParam String ifscCode) {
        logger.info("get branch by ifsc code");
        Response<Object> response = bankService.searchAndGetBranch(bankId, ifscCode);
        return ResponseEntity
                .status(response.getResponseStatus())
                .body(response);
    }
}
