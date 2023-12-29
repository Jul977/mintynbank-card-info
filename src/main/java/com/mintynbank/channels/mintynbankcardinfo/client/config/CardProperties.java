package com.mintynbank.channels.mintynbankcardinfo.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */

@Data
@Component
@ConfigurationProperties(prefix = "card.lookup")
public class CardProperties {

    private String baseUrl;
}
