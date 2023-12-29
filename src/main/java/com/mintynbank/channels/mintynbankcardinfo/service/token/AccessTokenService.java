package com.mintynbank.channels.mintynbankcardinfo.service.token;

import com.mintynbank.channels.mintynbankcardinfo.repository.token.AccessTokenRepository;
import com.mintynbank.channels.mintynbankcardinfo.repository.token.model.AccessTokenModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
@Slf4j
@Service
public class AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;

    public AccessTokenService(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    public void saveAccessToken(AccessTokenModel accessTokenModel) {
        accessTokenRepository.save(accessTokenModel);
    }

    public Optional<AccessTokenModel> getToken(String token) {
        return accessTokenRepository.findByToken(token);
    }

    public Optional<AccessTokenModel> getByCustomerId(String customerId) {
        return accessTokenRepository.findByCustomerId(customerId);
    }

    public int updateAccessToken(AccessTokenModel tokenModel) {
        return accessTokenRepository.updateAccessToken(
                tokenModel.getId(),
                tokenModel.getToken(),
                tokenModel.getCreatedAt(),
                tokenModel.getExpiresAt(),
                tokenModel.getLoggedInAt());
    }

    public boolean validateToken(String token) {
        Optional<AccessTokenModel> accessTokenModel = getToken(token);
        if(accessTokenModel.isEmpty()) {
            log.debug("token does not exist in database");
            return false;
        }
        AccessTokenModel tokenModel = accessTokenModel.get();
        // check that the token supplied is the same with the token in database
        if(!tokenModel.getToken().equals(token)) {
            log.debug("token is invalid");
            return false;
        }
        // check that the token is not expired
        if(tokenModel.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.debug("token is expired");
            return false;
        }
        //TODO check that the customer is enabled
        return true;
    }
}
