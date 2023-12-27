package com.minitynbank.channels.mintynbankcardinfo.api.customer;

import com.minitynbank.channels.mintynbankcardinfo.api.customer.request.CustomerRegistrationRequest;
import com.minitynbank.channels.mintynbankcardinfo.api.customer.response.CustomerBaseResponse;
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
@RequestMapping(path = "api/v1/registration")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public JsonResult<CustomerBaseResponse> register(@RequestBody CustomerRegistrationRequest request) {
        CustomerBaseResponse response =  customerService.register(request);
        return JsonResult.signUpResult(response);
    }
}
