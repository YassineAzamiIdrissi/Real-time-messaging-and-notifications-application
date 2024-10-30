package com.example.real_time.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine engine;

    public void sendEmail(String to,
                          String username,
                          EmailTemplate emailTemplate,
                          String activationCode,
                          String confirmationUrl,
                          String subject)
            throws MessagingException {
        String template;
        if (emailTemplate == null) {
            template = "recover_password";
        } else {
            template = emailTemplate.getName();
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        Map<String, Object> props = new HashMap<>();
        props.put("username", username);
        props.put("activation_code", activationCode);
        props.put("confirmationUrl", confirmationUrl);

        Context context = new Context();
        context.setVariables(props);

        helper.setFrom("somesortofsupportemail@smtp.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String processed = engine.process(template, context);
        helper.setText(processed, true);
        mailSender.send(message);
    }
}
