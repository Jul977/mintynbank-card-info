package com.minitynbank.channels.mintynbankcardinfo.service;

import com.minitynbank.channels.mintynbankcardinfo.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.minitynbank.channels.mintynbankcardinfo.common.constants.Constants.CUSTOMER_NOT_FOUND;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * @param email email of the customer
     * @return user
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(CUSTOMER_NOT_FOUND, email)));
    }
}
