package com.mintynbank.channels.mintynbankcardinfo.api.customer.util;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */

@Service
public class EmailValidator implements Predicate<String> {
    // Common regex pattern for basic email validation
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$";

    @Override
    public boolean test(String email) {
        // Validate email using the regex pattern
        return Pattern.matches(EMAIL_REGEX, email);
    }
}
