package com.mintynbank.channels.mintynbankcardinfo.api.card.response;

import com.mintynbank.channels.mintynbankcardinfo.api.customer.response.CustomerRegistrationResponse;
import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 29/12/2023
 */
@Data
public class CardJsonResult<T>{

    private boolean status;

    private String message;

    private String token;

    private CardJsonResult() {
    }

    public static <T> CardJsonResult<T> lookUpResult(CustomerRegistrationResponse response) {
        CardJsonResult<T> result = new CardJsonResult<>();
        result.setStatus(response.getStatus());
        result.setMessage(response.getMessage());
        return result;
    }
}
