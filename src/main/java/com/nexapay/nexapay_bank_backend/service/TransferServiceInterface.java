package com.nexapay.nexapay_bank_backend.service;

import com.nexapay.dto.request.TransferRequest;
import com.nexapay.dto.response.Response;
import com.nexapay.dto.response.TransferResponse;

import java.util.List;

public interface TransferServiceInterface {
    Response<TransferResponse> createTransfer(TransferRequest transferRequest);

    Response<List<TransferResponse>> getTransfer(String accountNo);
}
