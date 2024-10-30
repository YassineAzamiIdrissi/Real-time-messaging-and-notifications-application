package com.example.real_time.Authentication;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("register")
    ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest registrationReq)
            throws MessagingException {
        authService.register(registrationReq);
        return ResponseEntity.ok().
                build();
    }

    @PostMapping("authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authRequest) {
        return ResponseEntity.ok().
                body(authService.authenticate(authRequest));
    }

    @GetMapping("activate-account")
    ResponseEntity<?> activateAccount(@RequestParam String code)
            throws MessagingException {
        authService.activateAccount(code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("demand-recovery")
    ResponseEntity<?> demandRecovery(@RequestParam(name = "email") String email)
            throws MessagingException {
        authService.demandRecoverPassword(email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("check-recovery")
    ResponseEntity<?> checkRecovery(@RequestParam(name = "recovery") String recovery)
            throws MessagingException {
        authService.checkRecoveryCode(recovery);
        return ResponseEntity.ok().
                build();
    }


    @PatchMapping("new-password/{recovery}")
    ResponseEntity<?> setNewPassword(@PathVariable("recovery") String recovery,
                                     @Valid @RequestBody PasswordsCoupleRequest couple) {
        authService.setNewPassword(recovery, couple);
        return ResponseEntity.ok().
                build();
    }
}
