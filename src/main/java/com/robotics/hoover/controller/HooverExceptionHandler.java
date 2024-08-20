package com.robotics.hoover.controller;

import com.robotics.hoover.model.HooverResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HooverExceptionHandler {
    @ExceptionHandler
    public ResponseEntity <HooverResponse> exceptionHandler(NullPointerException e){
        HooverResponse hooverResponse = new HooverResponse();
        return  ResponseEntity.ok(hooverResponse);
    }
    public ResponseEntity <HooverResponse> exceptionHandler(IllegalArgumentException e){
        HooverResponse hooverResponse = new HooverResponse();
        return  ResponseEntity.ok(hooverResponse);
    }
}
