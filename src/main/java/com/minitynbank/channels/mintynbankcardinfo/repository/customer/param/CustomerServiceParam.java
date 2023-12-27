package com.minitynbank.channels.mintynbankcardinfo.repository.customer.param;

import com.minitynbank.channels.mintynbankcardinfo.common.enums.CustomerRoleEnum;
import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Data
public class CustomerServiceParam {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private CustomerRoleEnum role;
}
