package com.minitynbank.channels.mintynbankcardinfo.api.customer;

import com.minitynbank.channels.mintynbankcardinfo.api.customer.request.CustomerLoginRequest;
import com.minitynbank.channels.mintynbankcardinfo.api.customer.request.CustomerRegistrationRequest;
import com.minitynbank.channels.mintynbankcardinfo.api.customer.response.CustomerLoginResponse;
import com.minitynbank.channels.mintynbankcardinfo.api.customer.response.CustomerRegistrationResponse;
import com.minitynbank.channels.mintynbankcardinfo.api.customer.response.JsonResult;
import com.minitynbank.channels.mintynbankcardinfo.service.customer.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/registration")
    public JsonResult<CustomerRegistrationResponse> register(@RequestBody CustomerRegistrationRequest request) {
        CustomerRegistrationResponse response =  customerService.register(request);
        return JsonResult.signUpResult(response);
    }

    @PostMapping(value = "/login")
    public JsonResult<CustomerLoginResponse> register(@RequestBody CustomerLoginRequest request) {
        CustomerLoginResponse response =  customerService.login(request);
        return JsonResult.loginResult(response);
    }
}
