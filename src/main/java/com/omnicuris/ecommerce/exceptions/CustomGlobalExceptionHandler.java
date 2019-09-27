package com.omnicuris.ecommerce.exceptions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({OutOfStockProductsException.class})
    public ResponseEntity<CustomErrorResponse> elementNotFound(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(Arrays.asList(ex.getMessage()));
        errors.setStatus(HttpStatus.BAD_REQUEST.value());   

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }
        @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
    		MethodArgumentNotValidException  ex,
    		HttpHeaders headers, HttpStatus status, WebRequest request)
    {
    
    	List<String> errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    	CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(errorMsg);
        errors.setStatus(HttpStatus.BAD_REQUEST.value());   
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    
    }
    /*@ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
      ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + 
              violation.getPropertyPath() + ": " + violation.getMessage());
        }
     
        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError(errors);
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());  
        return new ResponseEntity<Object>(
          errorResponse,HttpStatus.UNAUTHORIZED);
    }*/
}   
