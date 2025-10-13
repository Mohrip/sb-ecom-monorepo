package com.StackShop.project.GlobalExceptionHandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> myMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String , String> response = new HashMap<>();
        ex.getBindingResult(). getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            response.put(fieldName, message);
        });
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
}

//public ResponseEntity<String> myResponseNotFoundExceptionHandler(Exception ex){
//    return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
//}




}
