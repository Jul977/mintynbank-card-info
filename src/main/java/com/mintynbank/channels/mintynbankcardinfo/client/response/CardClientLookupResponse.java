package com.mintynbank.channels.mintynbankcardinfo.client.response;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
@Slf4j
@Data
public class CardClientLookupResponse {

    private Number number;

    private String scheme;

    private String type;

    private String brand;

    private Boolean prepaid;

    private Country country;

    private Bank bank;

    @Data
    public static class Number {
        private Long length;
    }

    @Data
    public static class Country {

        private String numeric;

        private String alpha2;

        private String name;

        private String emoji;

        private String currency;

        private Long latitude;

        private Long longitude;
    }

    @Data
    public static class Bank {

        private String name;

        private String url;

        private String phone;

        private String city;
    }

    public static CardClientLookupResponse build(String responseStr) {
        try {
            return JSONObject.parseObject(responseStr, CardClientLookupResponse.class);
        } catch (Exception e) {
            log.error("parse card lookup response from string to object exception, responseString:{}", responseStr);
            return null;
        }
    }
}
