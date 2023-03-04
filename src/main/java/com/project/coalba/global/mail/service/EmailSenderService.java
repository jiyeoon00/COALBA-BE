package com.project.coalba.global.mail.service;

import com.project.coalba.global.mail.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailMessage.getTo());
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getContent() + " 초대 링크: " + emailMessage.getInvitationLink());
        mailSender.send(message);
    }
}
