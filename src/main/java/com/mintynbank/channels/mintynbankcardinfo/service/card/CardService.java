package com.mintynbank.channels.mintynbankcardinfo.service.card;

import com.mintynbank.channels.mintynbankcardinfo.api.card.response.CardVerifyResponse;
import com.mintynbank.channels.mintynbankcardinfo.client.CardClient;
import com.mintynbank.channels.mintynbankcardinfo.client.response.CardClientLookupResponse;
import com.mintynbank.channels.mintynbankcardinfo.client.response.ClientRequestResponse;
import com.mintynbank.channels.mintynbankcardinfo.service.card.convert.CardServiceConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.mintynbank.channels.mintynbankcardinfo.common.constants.Constants.*;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
@Slf4j
@Service
public class CardService {

    private final CardClient cardClient;

    public CardService(CardClient cardClient) {
        this.cardClient = cardClient;
    }

    public CardVerifyResponse verifyCard(String cardBin) {

        // call the third-party client to verify the bin
        ClientRequestResponse clientRequestResponse = cardClient.doCardLookUp(cardBin);
        log.info("card verify client requestResponse, {}", clientRequestResponse);
        CardClientLookupResponse clientResponse = CardClientLookupResponse.build(clientRequestResponse.getResponseBody());

        if(SUCCESS_CODE_200.equals(clientRequestResponse.getResponseCode()) && clientResponse != null) {
            log.info("card verify service response, response:{}", clientResponse);
            return CardServiceConvert.convertToResponse(clientResponse, true);
        }

        if(ERROR_CODE_429.equals(clientRequestResponse.getResponseCode())) {
            return CardServiceConvert.toLimitExceededResponse();
        }

        // else return false
        return CardServiceConvert.convertToResponse(clientResponse, false);
    }
}
