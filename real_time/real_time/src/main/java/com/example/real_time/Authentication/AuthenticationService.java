package com.example.real_time.Authentication;

import com.example.real_time.ActivationCode.ActivationCode;
import com.example.real_time.ActivationCode.ActivationCodeRepository;
import com.example.real_time.Authority.Authority;
import com.example.real_time.Authority.AuthorityRepository;
import com.example.real_time.CustomExceptions.*;
import com.example.real_time.Email.EmailService;
import com.example.real_time.Email.EmailTemplate;
import com.example.real_time.RecoveryCode.RecoveryCode;
import com.example.real_time.RecoveryCode.RecoveryCodeRepository;
import com.example.real_time.Security.JwtService;
import com.example.real_time.User.User;
import com.example.real_time.User.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.real_time.Email.EmailTemplate.ACTIVATE_ACCOUNT;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepo;
    private final ActivationCodeRepository activationCodeRepo;
    private final RecoveryCodeRepository recoveryCodeRepo;
    private final AuthorityRepository authorityRepo;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final EmailService emailService;

    @Value("${application.front.urls.activation}")
    private String activationUrl;
    @Value("${application.front.urls.recovery}")
    private String recoveryUrl;


    public AuthenticationResponse authenticate(AuthenticationRequest req) {
        UsernamePasswordAuthenticationToken
                authToken = new UsernamePasswordAuthenticationToken(
                req.getEmail(),
                req.getPassword()
        );
        var authentication = authenticationManager.authenticate(authToken);
        Map<String, Object> claims = new HashMap<>();
        User user = (User) authentication.getPrincipal();
        claims.put("userId", user.getId());
        claims.put("fullName", user.fullName());
        String token = jwtService.generateToken(user, claims);
        return AuthenticationResponse.builder().
                token(token).
                build();
    }

    public void register(RegistrationRequest req)
            throws MessagingException {
        Authority userAuth = authorityRepo.findByAuthority("USER").
                orElseThrow(() ->
                        new AuthoritiesNotInitializedYetException
                                ("Default authority not defined ! "));
        User user = User.builder().
                email(req.getEmail()).
                password(passwordEncoder.encode(req.getPassword())).
                firstName(req.getFirstName()).
                lastName(req.getLastName()).
                authorities(List.of(userAuth)).
                locked(false).
                enabled(false).
                build();
        userRepo.save(user);
        sendEmail(user, true);
    }

    public void setNewPassword(String recoveryCode,
                               PasswordsCoupleRequest req) {
        if (!Objects.equals(req.getNewPassword(), req.getConfirmNewPassword())) {
            throw new PasswordsMisMatchException("Passwords aren't similar..");
        }
        RecoveryCode savedCode = recoveryCodeRepo.findByRecoveryCode(
                recoveryCode
        ).orElseThrow(
                () -> new IncorrectRecoveryCodeException("Recovery code not found.")
        );
        User concernedUser = savedCode.getUser();
        concernedUser.setPassword(passwordEncoder.encode(
                req.getNewPassword()
        ));
        userRepo.save(concernedUser);
    }

    public void checkRecoveryCode(String recoveryCode)
            throws MessagingException {
        RecoveryCode savedCode = recoveryCodeRepo.
                findByRecoveryCode(recoveryCode).
                orElseThrow(() -> new IncorrectRecoveryCodeException
                        ("Incorrect recovery code : " + recoveryCode));
        if (savedCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            sendEmail(savedCode.getUser(), false);
            throw new RecoveryCodeExpiredException
                    ("Recovery code expired.. a new one has been sent");
        }
        savedCode.setUsed(true);
        recoveryCodeRepo.save(savedCode);
    }

    public void demandRecoverPassword(String email)
            throws MessagingException {
        if (email.isEmpty()) {
            throw new InvalidOperationException("Email can't be empty !");
        }
        User user = userRepo.findByEmail(email).
                orElseThrow(() -> new
                        AppUserNotFoundException
                        ("user with email " + email + " isn't found"));
        sendEmail(user, false);
    }

    public void activateAccount(String activationCode)
            throws MessagingException {
        ActivationCode savedCode = activationCodeRepo.findByActivationCode(
                activationCode
        ).orElseThrow(() -> new IncorrectActivationCode
                ("The given code " + activationCode + " is not correct !"));
        if (savedCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            sendEmail(savedCode.getUser(), true);
            throw new ActivationCodeExpiredException
                    ("The code" + activationCode + " you gave is expired.. " +
                            "a new one has been already sent to " +
                            savedCode.getUser().getEmail() + " !");
        }
        User user = savedCode.getUser();
        savedCode.setUpdatedAt(LocalDateTime.now());
        user.setEnabled(true);

        activationCodeRepo.save(savedCode);
        userRepo.save(user);
    }

    private void sendEmail(User user, boolean isForActivation)
            throws MessagingException {
        EmailTemplate template;
        String code;
        String subject;
        String redirection;
        if (isForActivation) {
            subject = "Activate your account";
            code = generateAndSaveActivationCode(user);
            template = ACTIVATE_ACCOUNT;
            redirection = activationUrl;
        } else {
            subject = "Password recovery";
            code = generateAndSaveRecoveryCode(user);
            template = null;
            redirection = recoveryUrl;
        }
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                template,
                code,
                redirection,
                subject
        );
    }

    private String generateAndSaveRecoveryCode(User user) {
        String rawCode = generateCode(10);
        RecoveryCode recoveryCode =
                RecoveryCode.builder().
                        recoveryCode(rawCode).
                        user(user).
                        createdAt(LocalDateTime.now()).
                        expiresAt(LocalDateTime.now().plusMinutes(10)).
                        build();
        recoveryCodeRepo.save(recoveryCode);
        return rawCode;
    }

    private String generateAndSaveActivationCode(User user) {
        String rawCode = generateCode(6);
        ActivationCode activationCode =
                ActivationCode.builder().
                        activationCode(rawCode).
                        user(user).
                        createdAt(LocalDateTime.now()).
                        expiresAt(LocalDateTime.now().plusMinutes(10)).
                        build();
        activationCodeRepo.save(activationCode);
        return rawCode;
    }

    private String generateCode(int length) {
        String chars = "0123456789";
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randIndex = random.nextInt(chars.length());
            code.append(chars.charAt(randIndex));
        }
        return code.toString();
    }
}
