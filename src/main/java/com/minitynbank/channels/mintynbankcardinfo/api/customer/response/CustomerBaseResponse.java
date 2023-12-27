package com.minitynbank.channels.mintynbankcardinfo.api.customer.response;

import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Data
public class CustomerBaseResponse {

    private boolean status;

    private String message;

    private String token;

    public boolean getStatus() {
        return this.status;
    }
}
