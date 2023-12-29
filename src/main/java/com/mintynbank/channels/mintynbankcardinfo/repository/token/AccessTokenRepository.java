package com.mintynbank.channels.mintynbankcardinfo.repository.token;

import com.mintynbank.channels.mintynbankcardinfo.repository.token.model.AccessTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
@Repository
public interface AccessTokenRepository extends JpaRepository<AccessTokenModel, Long> {

    Optional<AccessTokenModel> findByToken(String token);

    Optional<AccessTokenModel> findByCustomerId(String customerId);


    @Transactional
    @Modifying
    @Query("UPDATE token_data c " +
            "SET c.token = :token, " +
            "c.createdAt = :createdAt, " +
            "c.expiresAt = :expiresAt, " +
            "c.loggedInAt = :loggedInAt " +
            "WHERE c.id = :id")
    int updateAccessToken(@Param("id") Long id,
                          @Param("token") String token,
                          @Param("createdAt") LocalDateTime createdAt,
                          @Param("expiresAt") LocalDateTime expiresAt,
                          @Param("loggedInAt") LocalDateTime loggedInAt);
}

