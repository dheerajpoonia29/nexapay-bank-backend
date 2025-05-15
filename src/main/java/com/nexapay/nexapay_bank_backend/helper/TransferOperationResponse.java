package com.nexapay.nexapay_bank_backend.helper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransferOperationResponse {
    private Boolean status;

    private String msg;
}
