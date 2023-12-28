package com.minitynbank.channels.mintynbankcardinfo.repository.customer.convert;

import com.minitynbank.channels.mintynbankcardinfo.api.customer.request.CustomerLoginRequest;
import com.minitynbank.channels.mintynbankcardinfo.api.customer.request.CustomerRegistrationRequest;
import com.minitynbank.channels.mintynbankcardinfo.common.enums.CustomerRoleEnum;
import com.minitynbank.channels.mintynbankcardinfo.repository.customer.model.CustomerModel;
import com.minitynbank.channels.mintynbankcardinfo.repository.customer.param.CustomerLoginServiceParam;
import com.minitynbank.channels.mintynbankcardinfo.repository.customer.param.CustomerRegistrationServiceParam;
import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Data
public class CustomerServiceConvert {

    public static CustomerRegistrationServiceParam convertToServiceParam (CustomerRegistrationRequest request) {
        if (request == null) {
            return null;
        }
        CustomerRegistrationServiceParam customerRegistrationServiceParam = new CustomerRegistrationServiceParam();
        customerRegistrationServiceParam.setFirstName(request.getFirstName());
        customerRegistrationServiceParam.setLastName(request.getLastName());
        customerRegistrationServiceParam.setEmail(request.getEmail());
        customerRegistrationServiceParam.setPhoneNumber(request.getPhoneNumber());
        customerRegistrationServiceParam.setPassword(request.getPassword());
        customerRegistrationServiceParam.setRole(CustomerRoleEnum.USER);
        return customerRegistrationServiceParam;
    }

    public static CustomerModel convertToModel (CustomerRegistrationServiceParam serviceParam) {
        if (serviceParam == null) {
            return null;
        }
        CustomerModel customerModel = new CustomerModel();
        customerModel.setFirstName(serviceParam.getFirstName());
        customerModel.setLastName(serviceParam.getLastName());
        customerModel.setEmail(serviceParam.getEmail());
        customerModel.setPhoneNumber(serviceParam.getPhoneNumber());
        customerModel.setPassword(serviceParam.getPassword());
        customerModel.setRole(serviceParam.getRole());
        customerModel.setEnabled(true);
        return customerModel;
    }

    public static CustomerLoginServiceParam convertToServiceParam (CustomerLoginRequest request) {
        if (request == null) {
            return null;
        }
        CustomerLoginServiceParam customerLoginServiceParam = new CustomerLoginServiceParam();
        customerLoginServiceParam.setUsername(request.getEmail());
        customerLoginServiceParam.setPassword(request.getPassword());
        return customerLoginServiceParam;
    }
}
