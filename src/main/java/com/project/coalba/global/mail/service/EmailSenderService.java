package com.project.coalba.global.mail.service;

import com.project.coalba.global.exception.*;
import com.project.coalba.global.mail.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;

    @Async
    public void send(EmailMessage emailMessage) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(emailMessage.getText(), emailMessage.isHtmlText());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(ErrorCode.EMAIL_SEND_ERROR);
        }
    }
}
