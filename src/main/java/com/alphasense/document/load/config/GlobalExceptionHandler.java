package com.alphasense.document.load.config;

import com.alphasense.document.load.exception.ApiException;
import com.alphasense.document.load.dto.Error;
import com.alphasense.document.load.dto.Errors;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * This class is a global exception handler class to handle all the exceptions in the application.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This method is used to handle the @link {ApiException.class}.
     *
     * @param ex - represents the ApiException Object.
     * @return - response entity with list of errors.
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleApiException(
            ApiException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(buildResponseEntity(ex.getErrors()));
    }

    /**
     * This is an utility method used to create error object.
     *
     * @param errorList -  represents the list of errors.
     * @return Errors object.
     */
    private Errors buildResponseEntity(List<Error> errorList) {
        Errors errors = new Errors();
        errors.setErrorList(errorList);
        return errors;
    }

}
