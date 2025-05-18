package com.nexapay.nexapay_bank_backend.service;

import com.nexapay.dto.request.TransferRequest;
import com.nexapay.dto.response.Response;
import com.nexapay.helper.HelperUtil;
import com.nexapay.nexapay_bank_backend.dao.TransferDAO;
import com.nexapay.dto.response.TransferResponse;
import com.nexapay.nexapay_bank_backend.helper.TransferOperationResponse;
import com.nexapay.nexapay_bank_backend.helper.TransferOperation;
import com.nexapay.model.TransferEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.nexapay.nexapay_bank_backend.helper.ResponseUtil.createResponse;

@Service
public class TransferService implements TransferServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    @Autowired
    TransferDAO transferDAO;

    @Autowired
    TransferOperation transferOperation;

    public Response<TransferResponse> createTransfer(TransferRequest transferRequest) {
        logger.info("create transfer entity");
        TransferEntity transferEntity = TransferEntity.toEntity(transferRequest);

        logger.info("generate transfer id");
        transferEntity.setTransferId(HelperUtil.generateTransactionId());

        logger.info("do transfer operation");
        TransferOperationResponse TransferOperation = this.transferOperation.doTransferOperation(transferEntity);
        transferEntity.setStatus(TransferOperation.getStatus());
        transferEntity.setStatusInfo(TransferOperation.getMsg());

        logger.info("persist transfer");
        boolean persistStatus = transferDAO.saveTransfer(transferEntity);

        logger.info("create transfer response");
        if(persistStatus) {
            return createResponse(
                    HttpStatus.CREATED,
                    "transfer created",
                    transferEntity,
                    entity -> TransferResponse.builder()
                            .transferId(transferEntity.getTransferId())
                            .fromAccountNo(transferEntity.getFromAccountNo())
                            .toAccountNo(transferEntity.getToAccountNo())
                            .amount(transferEntity.getAmount())
                            .date(transferEntity.getDate())
                            .status(transferEntity.getStatus())
                            .statusInfo(transferEntity.getStatusInfo()).build());
        }
        transferEntity.setStatus(false);
        transferEntity.setStatusInfo("internal server error");
        return createResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                transferEntity.getStatusInfo(),
                null
                ,null);
    }

    @Override
    public Response<List<TransferResponse>> getTransfer(String accountNo) {
        logger.info("find transfer");
        // todo handle timeout or SqlExceptionHelper
        List<TransferEntity> transfer = transferDAO.getTransfers(accountNo, accountNo);

        logger.info("return response");
        List<TransferResponse> transferResponseList = new ArrayList<>();
        for (TransferEntity transferEntity : transfer) {
            transferResponseList.add(TransferResponse.builder()
                    .transferId(transferEntity.getTransferId())
                    .fromAccountNo(transferEntity.getFromAccountNo())
                    .toAccountNo(transferEntity.getToAccountNo())
                    .amount(transferEntity.getAmount())
                    .date(transferEntity.getDate())
                    .status(transferEntity.getStatus())
                    .statusInfo(transferEntity.getStatusInfo()).build());
        }
        return Response.<List<TransferResponse>>builder()
                .responseStatus(HttpStatus.OK)
                .responseStatusInt(HttpStatus.OK.value())
                .responseMsg("transfer found")
                .responseData(transferResponseList).build();
    }
}
