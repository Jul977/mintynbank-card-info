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

    public static CardVerifyResponse convertToResponse(CardClientLookupResponse clientResponse) {
        CardVerifyResponse cardVerifyResponse = new CardVerifyResponse();
        if (clientResponse == null) {
            cardVerifyResponse.setSuccess(false);
            cardVerifyResponse.setMessage(FAILED);
            return cardVerifyResponse;
        }
        cardVerifyResponse.setSuccess(true);
        cardVerifyResponse.setMessage(SUCCESS);
        CardVerifyResponse.Payload payload = new CardVerifyResponse.Payload();
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
