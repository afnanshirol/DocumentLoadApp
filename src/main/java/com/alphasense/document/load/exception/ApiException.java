package com.alphasense.document.load.exception;

import com.alphasense.document.load.dto.Error;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ApiException extends RuntimeException {

    List<Error> errors = new ArrayList<>();

    HttpStatus httpStatus;

    public ApiException(List<Error> errors, HttpStatus httpStatus) {
        this.errors = errors;
        this.httpStatus = httpStatus;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
