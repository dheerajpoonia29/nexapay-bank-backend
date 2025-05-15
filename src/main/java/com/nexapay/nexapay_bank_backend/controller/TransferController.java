package com.nexapay.nexapay_bank_backend.controller;

import com.nexapay.dto.request.TransferRequest;
import com.nexapay.dto.response.TransferResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.nexapay_bank_backend.service.TransferService;
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
public class TransferController implements TransferControllerInterface {
    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    @Autowired
    TransferService transferService;

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
    @PostMapping("/transfer")
    public ResponseEntity<Response<TransferResponse>> createTransfer(@RequestBody TransferRequest transferRequest) {
        logger.info("create transfer");
        Response<TransferResponse> response = transferService.createTransfer(transferRequest);
        logger.info("send response");
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @Override
    @GetMapping("/get-transfer")
    public ResponseEntity<Response<List<TransferResponse>>> listTransfer(@RequestParam("accountNo") String accountNo) {
        Response<List<TransferResponse>> response = transferService.getTransfer(accountNo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
