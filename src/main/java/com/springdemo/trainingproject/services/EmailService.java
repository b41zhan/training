package com.springdemo.trainingproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("satynbekovbaizhan@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Ваш код для подтверждения");
        message.setText("Здравствуйте! Ваш код для подтверждения смены пароля: " + code);

        mailSender.send(message);
    }
}
