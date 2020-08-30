package com.assHoleDeveloper.eCommerce.eCommerce.exception;

import com.assHoleDeveloper.eCommerce.eCommerce.commons.EcommerceUtils;
import com.assHoleDeveloper.eCommerce.eCommerce.controller.response.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private EcommerceUtils ecommerceUtils = new EcommerceUtils();

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        GenericResponse genericResponse = ecommerceUtils.generateResponse(ex.getLocalizedMessage(),
                null,null);
        return new ResponseEntity(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        GenericResponse genericResponse = ecommerceUtils.generateResponse(ex.getLocalizedMessage(),
                null,null);
        return new ResponseEntity(genericResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        GenericResponse genericResponse = ecommerceUtils.generateResponse(null,
                details,null);
        return new ResponseEntity(genericResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        GenericResponse genericResponse = ecommerceUtils.generateResponse(ex.getLocalizedMessage(),
                null,null);
        return new ResponseEntity(genericResponse, HttpStatus.BAD_REQUEST);
    }
}
