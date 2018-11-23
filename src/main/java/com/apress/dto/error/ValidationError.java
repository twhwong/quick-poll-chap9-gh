package com.apress.dto.error;

/**
 * 06-07-18
 *
 * @author Tom
 */

// use this custom class instead of the Spring error formats
public class ValidationError {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
