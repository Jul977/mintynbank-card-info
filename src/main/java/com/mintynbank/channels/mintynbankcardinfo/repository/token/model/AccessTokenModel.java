package com.mintynbank.channels.mintynbankcardinfo.repository.token.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
@Data
@EqualsAndHashCode
@Entity(name = "token_data")
@Table(
        name = "token_data",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_customer_id", columnNames = "customer_id")
        }
)
public class AccessTokenModel {

    @Id
    @SequenceGenerator(
            name = "access_token_sequence",
            sequenceName = "access_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "access_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(
            name = "create_time",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "expiry_time",
            nullable = false
    )
    private LocalDateTime expiresAt;

    @Column(
            name = "login_time",
            nullable = false
    )
    private LocalDateTime loggedInAt;

    @Column(
            name = "customer_id",
            nullable = false
    )
    private String customerId;
}
