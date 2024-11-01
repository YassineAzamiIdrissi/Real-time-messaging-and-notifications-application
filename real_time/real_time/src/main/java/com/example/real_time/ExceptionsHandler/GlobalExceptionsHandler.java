package com.example.real_time.ExceptionsHandler;

import com.example.real_time.CustomExceptions.*;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

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

    @ExceptionHandler(DisabledException.class)
    ResponseEntity<ExceptionRespDto> DisabledException(DisabledException exp) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage(exp.getMessage());
        return ResponseEntity.status(403).
                body(dto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ExceptionRespDto> BadCredentialsException(BadCredentialsException exp) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage("Email or password is incorrect !");
        return ResponseEntity.status(400).
                body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ExceptionRespDto> MethodArgumentNotValidException(
            MethodArgumentNotValidException exp
    ) {
        ExceptionRespDto dto = new ExceptionRespDto();
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors().forEach(
                err -> errors.add(err.getDefaultMessage())
        );
        dto.setErrors(errors);
        return ResponseEntity.status(400).
                body(dto);
    }

    @ExceptionHandler(PasswordsMisMatchException.class)
    ResponseEntity<ExceptionRespDto> PasswordsMisMatchException(
            PasswordsMisMatchException exp
    ) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage(exp.getMessage());
        return ResponseEntity.status(400).
                body(dto);
    }

    @ExceptionHandler(RecoveryCodeExpiredException.class)
    ResponseEntity<ExceptionRespDto> RecoveryCodeExpiredException(
            RecoveryCodeExpiredException exp
    ) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage(exp.getMessage());
        return ResponseEntity.status(410).
                body(dto);
    }

    @ExceptionHandler(ActivationCodeExpiredException.class)
    ResponseEntity<ExceptionRespDto> ActivationCodeExpiredException(
            ActivationCodeExpiredException exp
    ) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage(exp.getMessage());
        return ResponseEntity.status(410).
                body(dto);
    }

    @ExceptionHandler(MessagingException.class)
    ResponseEntity<ExceptionRespDto> MessagingException(
            MessagingException exp
    ) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage(exp.getMessage());
        return ResponseEntity.status(500).
                body(dto);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    ResponseEntity<ExceptionRespDto> SQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException exp
    ) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage("this Email is already used by another user !");
        return ResponseEntity.status(409).
                body(dto);
    }

    @ExceptionHandler(IncorrectActivationCode.class)
    ResponseEntity<ExceptionRespDto> IncorrectActivationCode(
            IncorrectActivationCode exp
    ) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage(exp.getMessage());
        return ResponseEntity.status(400).
                body(dto);
    }

    @ExceptionHandler(InvalidOperationException.class)
    ResponseEntity<ExceptionRespDto> InvalidOperationException(
            InvalidOperationException exp
    ) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage(exp.getMessage());
        return ResponseEntity.status(400).
                body(dto);
    }

    @ExceptionHandler(IncorrectRecoveryCodeException.class)
    ResponseEntity<ExceptionRespDto> IncorrectRecoveryCodeException(
            IncorrectRecoveryCodeException exp
    ) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage(exp.getMessage());
        return ResponseEntity.status(400).
                body(dto);
    }

    @ExceptionHandler(FriendRequestNotFoundException.class)
    ResponseEntity<ExceptionRespDto>
    FriendRequestNotFoundException(FriendRequestNotFoundException exp) {
        ExceptionRespDto dto = new ExceptionRespDto();
        dto.setMessage(exp.getMessage());
        return ResponseEntity.status(500).
                body(dto);
    }
}
