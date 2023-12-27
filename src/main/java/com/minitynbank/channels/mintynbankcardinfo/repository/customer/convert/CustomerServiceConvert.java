package com.minitynbank.channels.mintynbankcardinfo.repository.customer.convert;

import com.minitynbank.channels.mintynbankcardinfo.api.customer.request.CustomerRegistrationRequest;
import com.minitynbank.channels.mintynbankcardinfo.common.enums.CustomerRoleEnum;
import com.minitynbank.channels.mintynbankcardinfo.repository.customer.model.CustomerModel;
import com.minitynbank.channels.mintynbankcardinfo.repository.customer.param.CustomerServiceParam;
import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Data
public class CustomerServiceConvert {

    public static CustomerServiceParam convertToServiceParam (CustomerRegistrationRequest request) {
        if (request == null) {
            return null;
        }
        CustomerServiceParam customerServiceParam = new CustomerServiceParam();
        customerServiceParam.setFirstName(request.getFirstName());
        customerServiceParam.setLastName(request.getLastName());
        customerServiceParam.setEmail(request.getEmail());
        customerServiceParam.setPhoneNumber(request.getPhoneNumber());
        customerServiceParam.setPassword(request.getPassword());
        customerServiceParam.setRole(CustomerRoleEnum.USER);
        return customerServiceParam;
    }

    public static CustomerModel convertToModel (CustomerServiceParam serviceParam) {
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
}
