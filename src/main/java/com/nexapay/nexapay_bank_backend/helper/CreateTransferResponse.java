package com.nexapay.nexapay_bank_backend.helper;

import com.nexapay.dto.response.Response;
import com.nexapay.dto.response.TransferResponse;
import com.nexapay.model.TransferEntity;
import org.springframework.http.HttpStatus;

public class CreateTransferResponse {
    public static Response<TransferResponse> createResponse(HttpStatus httpStatus, String responseMsg,
                                                            TransferEntity transferEntity) {
        return Response.<TransferResponse>builder()
                .responseStatus(httpStatus)
                .responseStatusInt(HttpStatus.CREATED.value())
                .responseMsg(responseMsg)
                .responseData(TransferResponse.builder()
                        .transferId(transferEntity.getTransferId())
                        .fromAccountNo(transferEntity.getFromAccountNo())
                        .toAccountNo(transferEntity.getToAccountNo())
                        .amount(transferEntity.getAmount())
                        .date(transferEntity.getDate())
                        .status(transferEntity.getStatus())
                        .statusInfo(transferEntity.getStatusInfo()).build()).build();
    }
}
