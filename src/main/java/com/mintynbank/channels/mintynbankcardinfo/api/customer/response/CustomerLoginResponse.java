package com.mintynbank.channels.mintynbankcardinfo.api.customer.response;

import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
@Data
public class CustomerLoginResponse {

    private boolean status;

    private String message;

    private String token;

    public boolean getStatus() {
        return this.status;
    }
}
