package com.nexapay.nexapay_bank_backend.helper;

import com.nexapay.dto.response.Response;
import org.springframework.http.HttpStatus;

import java.util.function.Function;

public class ResponseUtil {

    public static <R, E> Response<R> createResponse(HttpStatus httpStatus, String responseMsg,
                                                    E entity, Function<E, R> mapper) {
        return Response.<R>builder()
                .responseStatus(httpStatus)
                .responseStatusInt(httpStatus.value())
                .responseMsg(responseMsg)
                .responseData(mapper.apply(entity))
                .build();
    }
}
