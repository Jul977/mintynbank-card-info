package com.mintynbank.channels.mintynbankcardinfo.api.customer.request;

import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 28/12/2023
 */
@Data
public class CustomerLoginRequest {

    private String email;

    private String password;
}
