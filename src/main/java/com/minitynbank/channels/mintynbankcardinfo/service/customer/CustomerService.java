package com.minitynbank.channels.mintynbankcardinfo.service.customer;

import com.minitynbank.channels.mintynbankcardinfo.api.customer.request.CustomerRegistrationRequest;
import com.minitynbank.channels.mintynbankcardinfo.api.customer.response.CustomerBaseResponse;
import com.minitynbank.channels.mintynbankcardinfo.api.customer.util.EmailValidator;
import com.minitynbank.channels.mintynbankcardinfo.repository.customer.CustomerRepository;
import com.minitynbank.channels.mintynbankcardinfo.repository.customer.convert.CustomerServiceConvert;
import com.minitynbank.channels.mintynbankcardinfo.repository.customer.model.CustomerModel;
import com.minitynbank.channels.mintynbankcardinfo.repository.customer.param.CustomerServiceParam;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.minitynbank.channels.mintynbankcardinfo.common.constants.Constants.*;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailValidator emailValidator;


    public CustomerService(CustomerRepository customerRepository, EmailValidator emailValidator, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailValidator = emailValidator;
    }

    /**
     * @param email email of the customer
     * @return user
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(CUSTOMER_NOT_FOUND, email)));
    }



    public CustomerBaseResponse register(CustomerRegistrationRequest request) {
        // validate the email supplied
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException(String.format(INVALID_EMAIL_SUPPLIED, request.getEmail()));
        }

        // signUp the customer
        CustomerServiceParam serviceParam = CustomerServiceConvert.convertToServiceParam(request);
        return signUpCustomer(serviceParam);
    }

    public CustomerBaseResponse signUpCustomer(CustomerServiceParam serviceParam) {
        CustomerModel customerRepModel = CustomerServiceConvert.convertToModel(serviceParam);
        // check if the customer already exists
        boolean customerExists = customerRepository.findByEmail(customerRepModel.getEmail()).isPresent();
        if (customerExists) {
            throw new IllegalStateException(String.format(EMAIL_ALREADY_TAKEN, serviceParam.getEmail()));
        }
        // encode the supplied password
        String encodedPassword = bCryptPasswordEncoder.encode(serviceParam.getPassword());

        customerRepModel.setPassword(encodedPassword);

        // save the customer to database
        customerRepository.save(customerRepModel);

        CustomerBaseResponse response = new CustomerBaseResponse();
        response.setStatus(true);
        response.setMessage("Registration Successful");
        return response;
    }
}
