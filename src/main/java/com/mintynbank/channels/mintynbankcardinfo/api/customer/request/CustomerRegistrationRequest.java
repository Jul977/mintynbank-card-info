package com.mintynbank.channels.mintynbankcardinfo.api.customer.request;

import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Data
public class CustomerRegistrationRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;
}
