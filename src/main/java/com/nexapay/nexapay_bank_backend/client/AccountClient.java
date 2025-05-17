package com.nexapay.nexapay_bank_backend.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexapay.dto.request.AccountRequest;
import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AccountClient {
    private static final Logger logger = LoggerFactory.getLogger(AccountClient.class);

    private final WebClient webClient;

    public AccountClient(WebClient.Builder builder, @Value("${user.account.api.host}") String userAccountApiHost) {
        logger.info("user api host: {}", userAccountApiHost);
        this.webClient = builder.baseUrl(userAccountApiHost).build();
    }

    public AccountResponse getAccount(String accountNo) {
        logger.info("get accountNo: {}", accountNo);

        return webClient.get()
                .uri("/account/get-by-account-no?accountNo={accountNo}", accountNo)
                .exchangeToMono(response -> {
                    HttpStatus status = (HttpStatus) response.statusCode();

                    if (status.is2xxSuccessful()) {
                        logger.info("received 2xx response");
                        return response.bodyToMono(String.class)
                                .doOnNext(body -> logger.info("raw response body: {}", body))
                                .flatMap(body -> {
                                    try {
                                        ObjectMapper mapper = new ObjectMapper();
                                        Response account = mapper.readValue(body, Response.class);
                                        AccountResponse accountResponse = mapper.convertValue(account.getResponseData(), AccountResponse.class);
                                        return Mono.just(accountResponse);
                                    } catch (JsonProcessingException e) {
                                        logger.error("failed to parse response", e);
                                        return Mono.error(new RuntimeException("parsing failed"));
                                    }
                                });
                    } else {
                        logger.error("failed to get account, error: {}", status);
                        return response.bodyToMono(String.class)
                                .doOnNext(errorBody -> logger.error("error body: {}", errorBody))
                                .then(Mono.empty());
                    }
                })
                .block();
    }

    public AccountResponse updateAccount(AccountRequest accountRequest) {
        logger.info("update accountNo: {}", accountRequest.getAccountNo());

        return webClient.post()
                .uri("/account/update-account")
                .bodyValue(accountRequest)
                .exchangeToMono(response -> {
                    HttpStatus status = (HttpStatus) response.statusCode();

                    if (status.is2xxSuccessful()) {
                        logger.info("received 2xx response");
                        return response.bodyToMono(String.class)
                                .doOnNext(body -> logger.info("raw response body: {}", body))
                                .flatMap(body -> {
                                    try {
                                        ObjectMapper mapper = new ObjectMapper();
                                        Response account = mapper.readValue(body, Response.class);
                                        AccountResponse accountResponse = mapper.convertValue(account.getResponseData(), AccountResponse.class);
                                        return Mono.just(accountResponse);
                                    } catch (JsonProcessingException e) {
                                        logger.error("failed to parse response", e);
                                        return Mono.error(new RuntimeException("parsing failed"));
                                    }
                                });
                    } else {
                        logger.error("failed to updated account, error: {}", status);
                        return response.bodyToMono(String.class)
                                .doOnNext(errorBody -> logger.error("error body: {}", errorBody))
                                .then(Mono.empty());
                    }
                })
                .block();
    }
}
