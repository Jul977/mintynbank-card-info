package com.mintynbank.channels.mintynbankcardinfo.service.customer;

import com.mintynbank.channels.mintynbankcardinfo.api.customer.request.CustomerLoginRequest;
import com.mintynbank.channels.mintynbankcardinfo.api.customer.request.CustomerRegistrationRequest;
import com.mintynbank.channels.mintynbankcardinfo.api.customer.response.CustomerLoginResponse;
import com.mintynbank.channels.mintynbankcardinfo.api.customer.response.CustomerRegistrationResponse;
import com.mintynbank.channels.mintynbankcardinfo.api.customer.util.EmailValidator;
import com.mintynbank.channels.mintynbankcardinfo.repository.customer.CustomerRepository;
import com.mintynbank.channels.mintynbankcardinfo.repository.customer.convert.CustomerServiceConvert;
import com.mintynbank.channels.mintynbankcardinfo.repository.customer.model.CustomerModel;
import com.mintynbank.channels.mintynbankcardinfo.repository.customer.param.CustomerLoginServiceParam;
import com.mintynbank.channels.mintynbankcardinfo.repository.customer.param.CustomerRegistrationServiceParam;
import com.mintynbank.channels.mintynbankcardinfo.repository.token.model.AccessTokenModel;
import com.mintynbank.channels.mintynbankcardinfo.service.token.AccessTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.mintynbank.channels.mintynbankcardinfo.common.constants.Constants.*;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final AccessTokenService accessTokenService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailValidator emailValidator;


    public CustomerService(CustomerRepository customerRepository, AccessTokenService accessTokenService, EmailValidator emailValidator, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.accessTokenService = accessTokenService;
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



    public CustomerRegistrationResponse register(CustomerRegistrationRequest request) {
        // validate the email supplied
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            return invalidEmailSupplied(request.getEmail());
        }
        // signUp the customer
        CustomerRegistrationServiceParam serviceParam = CustomerServiceConvert.convertToServiceParam(request);
        return signUpCustomer(serviceParam);
    }

    public CustomerRegistrationResponse signUpCustomer(CustomerRegistrationServiceParam serviceParam) {
        CustomerModel customerRepModel = CustomerServiceConvert.convertToModel(serviceParam);
        // check if the customer already exists
        boolean customerExists = customerRepository.findByEmail(customerRepModel.getEmail()).isPresent();
        if (customerExists) {
            return emailAlreadyTaken(customerRepModel.getEmail());
        }
        // encode the supplied password
        String encodedPassword = bCryptPasswordEncoder.encode(serviceParam.getPassword());

        customerRepModel.setPassword(encodedPassword);
        // save the customer to database
        customerRepository.save(customerRepModel);
        return registrationSuccess();
    }

    public CustomerLoginResponse login(CustomerLoginRequest request) {

        CustomerLoginServiceParam serviceParam = CustomerServiceConvert.convertToServiceParam(request);

        // fetch the customer from the database
        Optional<CustomerModel> customerModel = customerRepository.findByEmail(serviceParam.getUsername());

        if (customerModel.isEmpty()) {
            return invalidCredentials();
        }

        // fetch the customer
        CustomerModel customer = customerModel.get();

        // validate the password
        String userEnteredPassword = serviceParam.getPassword(); // This is the plain text password entered by the user
        String storedEncodedPassword = customer.getPassword(); // This is the hashed password stored in the database

        if (!bCryptPasswordEncoder.matches(userEnteredPassword, storedEncodedPassword)) {
            return invalidCredentials();
        }

        // fetch the customer's token record by id
        Optional<AccessTokenModel> oldTokenModel = accessTokenService.getByCustomerId(customer.getId().toString());

        if (oldTokenModel.isPresent()) {
            AccessTokenModel tokenModel = oldTokenModel.get();

            if (tokenModel.getExpiresAt().isBefore(LocalDateTime.now())) {
                // Update the token if expired
                tokenModel = updateToken(tokenModel);
                return (tokenModel != null) ? loginSuccessful(tokenModel.getToken()) : loginFailure();
            }
            return loginSuccessful(tokenModel.getToken());
        }

        // generate a new token for the customer
        String token = UUID.randomUUID().toString();

        AccessTokenModel tokenModel = new AccessTokenModel();
        tokenModel.setToken(token);
        tokenModel.setCreatedAt(LocalDateTime.now());
        tokenModel.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        tokenModel.setLoggedInAt(LocalDateTime.now());
        tokenModel.setCustomerId(customer.getId().toString());

        accessTokenService.saveAccessToken(tokenModel);

        return loginSuccessful(token);

    }

    private AccessTokenModel updateToken(AccessTokenModel tokenModel) {
        tokenModel.setToken(UUID.randomUUID().toString());
        tokenModel.setLoggedInAt(LocalDateTime.now());
        tokenModel.setExpiresAt(LocalDateTime.now().plusMinutes(10));

        int count = accessTokenService.updateAccessToken(tokenModel);

        return (count > 0) ? tokenModel : null;
    }

    private CustomerLoginResponse invalidCredentials () {
        CustomerLoginResponse response = new CustomerLoginResponse();
        response.setStatus(false);
        response.setMessage(INVALID_CREDENTIALS);
        return response;
    }

    private CustomerLoginResponse loginSuccessful (String token) {
        CustomerLoginResponse response = new CustomerLoginResponse();
        response.setStatus(true);
        response.setMessage(LOGIN_SUCCESSFUL);
        response.setToken(token);
        return response;
    }

    private CustomerLoginResponse loginFailure () {
        CustomerLoginResponse response = new CustomerLoginResponse();
        response.setStatus(false);
        response.setMessage(LOGIN_FAILURE);
        return response;
    }

    private CustomerRegistrationResponse registrationSuccess () {
        CustomerRegistrationResponse response = new CustomerRegistrationResponse();
        response.setStatus(true);
        response.setMessage(REGISTRATION_SUCCESSFUL);
        return response;
    }

    private CustomerRegistrationResponse invalidEmailSupplied (String email) {
        CustomerRegistrationResponse response = new CustomerRegistrationResponse();
        response.setStatus(false);
        response.setMessage(String.format(INVALID_EMAIL_SUPPLIED, email));
        return response;
    }

    private CustomerRegistrationResponse emailAlreadyTaken (String email) {
        CustomerRegistrationResponse response = new CustomerRegistrationResponse();
        response.setStatus(false);
        response.setMessage(String.format(EMAIL_ALREADY_TAKEN, email));
        return response;
    }
}
