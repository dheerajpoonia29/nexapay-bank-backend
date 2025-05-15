package com.nexapay.nexapay_bank_backend.service;

import com.nexapay.dto.response.BankResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.helper.BankBranch;
import com.nexapay.model.BankEntity;
import com.nexapay.nexapay_bank_backend.dao.BankDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.nexapay.nexapay_bank_backend.helper.ResponseUtil.createResponse;

@Service
public class BankService implements BankServiceInterface{

    private static final Logger logger = LoggerFactory.getLogger(BankService.class);

    @Autowired
    BankDAO bankDAO;

    @Override
    public Response<BankResponse> fetchSingleBank(Integer bankId) {
        logger.info("get bank");
        BankEntity bankEntity = bankDAO.getBankById(bankId);
        if(bankEntity==null) {
            logger.error("bank not found");
            return createResponse(
                    HttpStatus.NOT_FOUND,
                    "bank not found",
                    null,
                    entity -> null);
        }
        logger.info("bank found");
        return createResponse(
                HttpStatus.CREATED,
                "bank found",
                bankEntity,
                entity -> BankResponse.builder()
                        .id(bankEntity.getId())
                        .name(bankEntity.getName())
                        .branches(bankEntity.getBranches()).build());
    }

    @Override
    public Response<List<BankResponse>> fetchAllBanks() {
        logger.info("get bank");
        List<BankEntity> bankEntityList = bankDAO.getAllBanks();

        logger.info("bank entity list to response list");
        List<BankResponse> bankResponseList = new ArrayList<>();
        for (BankEntity bankEntity: bankEntityList) {
            bankResponseList.add(
                    BankResponse.builder()
                            .id(bankEntity.getId())
                            .name(bankEntity.getName())
                            .branches(bankEntity.getBranches()).build());
        }

        logger.info("make response");
        return Response.<List<BankResponse>>builder()
                .responseStatus(HttpStatus.OK)
                .responseStatusInt(HttpStatus.OK.value())
                .responseMsg("banks found")
                .responseData(bankResponseList).build();
    }

    @Override
    public Response<BankBranch> searchAndGetBranch(Integer bankId, String ifscCode) {
        logger.info("get bank by id");
        Response<BankResponse> bankResponse = fetchSingleBank(bankId);
        if(bankResponse.getResponseStatus()==HttpStatus.NOT_FOUND) {
            logger.error("bank not found");
            return createResponse(
                    HttpStatus.NOT_FOUND,
                    "bank not found",
                    null,
                    entity -> null);
        }

        logger.info("validate branches");
        List<BankBranch> bankBranchList = bankResponse.getResponseData().getBranches();

        logger.info("filter branch with ifsc code");
        BankBranch bankBranch = null;
        for (BankBranch obj: bankBranchList) {
            if(obj.getIfscCode().equals(ifscCode)) {
                bankBranch = obj;
                break;
            }
        }

        if(bankBranch==null) {
            logger.error("branch not found with ifsc code: {}", ifscCode);
            return createResponse(
                    HttpStatus.NOT_FOUND,
                    "bank not found",
                    null,
                    entity -> null);
        }

        logger.error("branch found with ifsc code: {}", ifscCode);
        return Response.builder()
                .responseStatus(HttpStatus.OK)
                .responseStatusInt(HttpStatus.OK.value())
                .responseMsg("branch found")
                .responseData(bankBranch).build();
    }

    /*
    add update delete branch feature
    bank.getBranches().add(new BankBranch(...));
    bankRepository.save(bank);
     */
}
