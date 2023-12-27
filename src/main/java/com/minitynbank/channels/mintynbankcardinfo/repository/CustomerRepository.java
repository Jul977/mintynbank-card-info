package com.minitynbank.channels.mintynbankcardinfo.repository;

import com.minitynbank.channels.mintynbankcardinfo.repository.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Emmanuel-Irabor
 * @since 26/12/2023
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

    Optional<CustomerModel> findByEmail(String email);
}
