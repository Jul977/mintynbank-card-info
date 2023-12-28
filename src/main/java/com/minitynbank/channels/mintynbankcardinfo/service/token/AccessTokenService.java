package com.minitynbank.channels.mintynbankcardinfo.service.token;

import com.minitynbank.channels.mintynbankcardinfo.repository.token.AccessTokenRepository;
import com.minitynbank.channels.mintynbankcardinfo.repository.token.model.AccessTokenModel;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
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
}
