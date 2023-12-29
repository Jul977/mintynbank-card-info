package com.mintynbank.channels.mintynbankcardinfo.api.customer;

import com.mintynbank.channels.mintynbankcardinfo.api.customer.request.CustomerLoginRequest;
import com.mintynbank.channels.mintynbankcardinfo.api.customer.request.CustomerRegistrationRequest;
import com.mintynbank.channels.mintynbankcardinfo.api.customer.response.CustomerLoginResponse;
import com.mintynbank.channels.mintynbankcardinfo.api.customer.response.CustomerRegistrationResponse;
import com.mintynbank.channels.mintynbankcardinfo.api.customer.response.JsonResult;
import com.mintynbank.channels.mintynbankcardinfo.service.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.mintynbank.channels.mintynbankcardinfo.common.constants.Constants.*;


/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Slf4j
@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/registration")
    public JsonResult<CustomerRegistrationResponse> register(@RequestBody CustomerRegistrationRequest request) {
        try {
            CustomerRegistrationResponse response =  customerService.register(request);
            return JsonResult.signUpResult(response);
        } catch (Exception e) {
            log.error("error occurred during customer registration", e);
            return createErrorResult();
        }
    }

    @PostMapping(value = "/login")
    public JsonResult<CustomerLoginResponse> login(@RequestBody CustomerLoginRequest request) {
        try {
            CustomerLoginResponse response = customerService.login(request);
            return JsonResult.loginResult(response);
        } catch (Exception e) {
            log.error("error occurred during customer login", e);
            return createErrorResult();
        }
    }

    private <T> JsonResult<T> createErrorResult() {
        return new JsonResult<>(false, INTERNAL_SERVER_ERROR, null);
    }
}
