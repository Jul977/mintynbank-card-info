package com.mintynbank.channels.mintynbankcardinfo.service.card.convert;

import com.mintynbank.channels.mintynbankcardinfo.api.card.response.CardVerifyResponse;
import com.mintynbank.channels.mintynbankcardinfo.client.response.CardClientLookupResponse;
import lombok.Data;

import static com.mintynbank.channels.mintynbankcardinfo.common.constants.Constants.*;

/**
 * @author Emmanuel-Irabor
 * @since 29/12/2023
 */
@Data
public class CardServiceConvert {

    public static CardVerifyResponse convertToResponse(CardClientLookupResponse clientResponse, boolean status) {
        CardVerifyResponse cardVerifyResponse = new CardVerifyResponse();
        if (clientResponse == null) {
            cardVerifyResponse.setSuccess(false);
            cardVerifyResponse.setMessage(FAILED);
            return cardVerifyResponse;
        }
        cardVerifyResponse.setSuccess(status);
        cardVerifyResponse.setMessage(SUCCESS);
        CardVerifyResponse.Payload payload = cardVerifyResponse.getPayload();
        payload.setScheme(clientResponse.getScheme());
        payload.setType(clientResponse.getType());
        payload.setBank(clientResponse.getBank().getName());
        cardVerifyResponse.setPayload(payload);
        return cardVerifyResponse;
    }

    public static CardVerifyResponse toLimitExceededResponse() {
        CardVerifyResponse cardVerifyResponse = new CardVerifyResponse();
        cardVerifyResponse.setSuccess(false);
        cardVerifyResponse.setMessage(SERVICE_UNAVAILABLE);
        return cardVerifyResponse;
    }
}
