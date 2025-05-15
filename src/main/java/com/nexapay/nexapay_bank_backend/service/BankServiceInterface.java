package com.nexapay.nexapay_bank_backend.service;

import com.nexapay.dto.response.BankResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.helper.BankBranch;

import java.util.List;

public interface BankServiceInterface {
    Response<BankResponse> fetchSingleBank(Integer bankId);

    Response<List<BankResponse>> fetchAllBanks();

    Response<BankBranch> searchAndGetBranch(Integer bankId, String ifscCode);
}
