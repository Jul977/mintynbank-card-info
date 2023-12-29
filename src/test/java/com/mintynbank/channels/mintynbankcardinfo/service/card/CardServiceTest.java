package com.mintynbank.channels.mintynbankcardinfo.service.card;

import com.mintynbank.channels.mintynbankcardinfo.MintynbankCardInfoApplication;
import com.mintynbank.channels.mintynbankcardinfo.api.card.response.CardVerifyResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Emmanuel-Irabor
 * @since 29/12/2023
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MintynbankCardInfoApplication.class)
public class CardServiceTest {

    @Autowired
    private CardService cardService;

    @Test
    public void testVerifyCard() {
        String testCardBin = "45717360";

        CardVerifyResponse cardVerifyResponse = cardService.verifyCard(testCardBin);
        assertNotNull(cardVerifyResponse);
    }
}
