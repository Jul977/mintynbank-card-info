package com.mintynbank.channels.mintynbankcardinfo.api.customer.response;

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

    public JsonResult(boolean status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public JsonResult() {
    }


    public static <T> JsonResult<T> signUpResult(CustomerRegistrationResponse response) {
        JsonResult<T> result = new JsonResult<>();
        result.setStatus(response.getStatus());
        result.setMessage(response.getMessage());
        return result;
    }

    public static <T> JsonResult<T> loginResult(CustomerLoginResponse response) {
        JsonResult<T> result = new JsonResult<>();
        result.setStatus(response.getStatus());
        result.setMessage(response.getMessage());
        result.setToken(response.getToken());
        return result;
    }
}
