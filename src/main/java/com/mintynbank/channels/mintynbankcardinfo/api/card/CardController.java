package com.mintynbank.channels.mintynbankcardinfo.api.card;

import com.mintynbank.channels.mintynbankcardinfo.api.card.response.CardVerifyResponse;
import com.mintynbank.channels.mintynbankcardinfo.service.card.CardService;
import com.mintynbank.channels.mintynbankcardinfo.service.token.AccessTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mintynbank.channels.mintynbankcardinfo.common.constants.Constants.*;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
@RestController
@RequestMapping(path = "api/v1/card-scheme")
public class CardController {

    private final CardService cardService;

    private final AccessTokenService accessTokenService;

    public CardController(CardService cardService, AccessTokenService accessTokenService) {
        this.cardService = cardService;
        this.accessTokenService = accessTokenService;
    }

    @GetMapping("/verify/{cardBin}")
    public ResponseEntity<CardVerifyResponse> verifyCard(@PathVariable String cardBin, @RequestHeader("Authorization") String authorizationHeader) {
        // extract the token from the header and validate the token
        String accessToken = extractTokenFromHeader(authorizationHeader);
        boolean isValid = accessTokenService.validateToken(accessToken);

        if(!isValid) {
            CardVerifyResponse response = new CardVerifyResponse();
            response.setSuccess(false);
            response.setMessage(INVALID_TOKEN_SUPPLIED);
            return ResponseEntity.ok(response);
        }

        try {
            CardVerifyResponse response = cardService.verifyCard(cardBin);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            CardVerifyResponse response = new CardVerifyResponse();
            response.setSuccess(false);
            response.setMessage(INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        // Assuming the Authorization header is in the format "Bearer <token>"
        String[] parts = authorizationHeader.split(" ");
        if (parts.length == 2) {
            return parts[1]; // Extract the token part
        } else {
            return null; // Invalid Authorization header format
        }
    }
}
