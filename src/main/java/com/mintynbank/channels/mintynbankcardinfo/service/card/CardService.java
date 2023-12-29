package com.mintynbank.channels.mintynbankcardinfo.service.card;

import com.mintynbank.channels.mintynbankcardinfo.api.card.response.CardVerifyResponse;
import com.mintynbank.channels.mintynbankcardinfo.client.CardClient;
import com.mintynbank.channels.mintynbankcardinfo.client.response.CardClientLookupResponse;
import com.mintynbank.channels.mintynbankcardinfo.client.response.ClientRequestResponse;
import com.mintynbank.channels.mintynbankcardinfo.service.card.convert.CardServiceConvert;
import com.mintynbank.channels.mintynbankcardinfo.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        if(Constants.SUCCESS_CODE_200.equals(clientRequestResponse.getResponseCode()) && clientRequestResponse.getResponseBody() != null) {
            CardClientLookupResponse clientResponse = CardClientLookupResponse.build(clientRequestResponse.getResponseBody());
            log.info("card verify service response, response:{}", clientResponse);
            return CardServiceConvert.convertToResponse(clientResponse, true);
        }
        return CardServiceConvert.convertToResponse(null, false);
    }
}
