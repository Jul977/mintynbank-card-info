package com.mintynbank.channels.mintynbankcardinfo.api.customer.util;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
//        TODO: Regex to validate email
        return true;
    }
}
