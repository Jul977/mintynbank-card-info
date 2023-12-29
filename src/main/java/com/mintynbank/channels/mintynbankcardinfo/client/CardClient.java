package com.mintynbank.channels.mintynbankcardinfo.client;

import com.mintynbank.channels.mintynbankcardinfo.client.config.CardProperties;
import com.mintynbank.channels.mintynbankcardinfo.client.response.ClientRequestResponse;
import com.mintynbank.channels.mintynbankcardinfo.client.util.TokenBucket;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.mintynbank.channels.mintynbankcardinfo.common.constants.Constants.*;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
@Slf4j
@Service
public class CardClient {

    private final CardProperties cardProperties;

    private final WebClient webClient;

    private final TokenBucket tokenBucket;

    public CardClient(CardProperties cardLookupProperties, TokenBucket tokenBucket) {
        this.cardProperties = cardLookupProperties;
        this.webClient = WebClient.create(cardLookupProperties.getBaseUrl());
        this.tokenBucket = tokenBucket;
    }

    public ClientRequestResponse doCardLookUp(String cardBin) {

        ClientRequestResponse result = new ClientRequestResponse();

        String endPoint = "/" + cardBin;
        result.setUrl(cardProperties.getBaseUrl() + endPoint);

        try {
            // Check if a token can be consumed
            if (tokenBucket.tryConsume()) {
                ApiResponse apiResponse = makeApiCall(endPoint);
                setRequestResponseInfo(apiResponse, result);
                log.info("Card lookup client api call, requestResponse:{}", result);
            } else {
                log.info("Rate limit exceeded. Unable to make API call.");
                result.setResponseCode(ERROR_CODE_429);
            }
        } catch (Exception e) {
            log.error("Card lookup client exception, reason:{}", e.getMessage());
            result.setResponseCode(ERROR_CODE_500);
        }
        return result;
    }

    public ApiResponse makeApiCall(String endpoint) {
        // Make a GET request
        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .toEntity(String.class)
                .map(responseEntity -> new ApiResponse(responseEntity.getStatusCode(), responseEntity.getBody()))
                .onErrorReturn(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, null))
                .block(); // block to get the result synchronously
    }

    @Data
    public static class ApiResponse {

        private final HttpStatus status;

        private final String responseBody;

    }

    private void setRequestResponseInfo(ApiResponse apiResponse, ClientRequestResponse requestResponse) {
        requestResponse.setResponseCode(String.valueOf(apiResponse.getStatus()));
        requestResponse.setResponseBody(apiResponse.getResponseBody());
    }
}
