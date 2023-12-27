package com.minitynbank.channels.mintynbankcardinfo.api.customer.response;

import lombok.Data;

/**
 * @author Emmanuel-Irabor
 * @since 27/12/2023
 */
@Data
public class JsonResult<T> {

    private boolean status;

    private String message;

    private String token;

    private JsonResult() {
    }

    public static <T> JsonResult<T> signUpResult(CustomerBaseResponse response) {
        JsonResult<T> result = new JsonResult<>();
        result.setStatus(response.getStatus());
        result.setMessage(response.getMessage());
        return result;
    }
}
