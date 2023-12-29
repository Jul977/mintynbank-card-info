package com.mintynbank.channels.mintynbankcardinfo.client.response;

import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
@Data
public class ClientRequestResponse {

    private String url = "";

    private String requestBody = "";

    private String responseBody = "";

    private String responseCode = "";
}
