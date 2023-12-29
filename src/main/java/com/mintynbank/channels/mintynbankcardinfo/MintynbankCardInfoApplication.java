package com.mintynbank.channels.mintynbankcardinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.mintynbank"
        }
)
public class MintynbankCardInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MintynbankCardInfoApplication.class, args);
    }

}
