package com.example.real_time.ExceptionsHandler;

import com.example.real_time.CustomExceptions.AppUserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<ExceptionRespDto> AppUserNotFoundException(
            AppUserNotFoundException exp
    ) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage(exp.getMessage());
        return ResponseEntity.status(404).
                body(dto);
    }
}
