package com.minitynbank.channels.mintynbankcardinfo.api.customer.response;

import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Data
public class CustomerRegistrationResponse {

    private boolean status;

    private String message;

    public boolean getStatus() {
        return this.status;
    }
}
