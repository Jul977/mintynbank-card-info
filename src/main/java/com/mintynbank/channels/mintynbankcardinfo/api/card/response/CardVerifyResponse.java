package com.mintynbank.channels.mintynbankcardinfo.api.card.response;

import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 29/12/2023
 */
@Data
public class CardVerifyResponse {

    private boolean success;

    private Payload payload;

    @Data
    public static class Payload {

        private String scheme;

        private String type;

        private String bank;
    }
}
