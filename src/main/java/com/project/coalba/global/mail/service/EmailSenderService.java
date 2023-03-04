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
    public void sendEmail(EmailMessage emailMessage) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(getHtmlText(emailMessage.getContent(), emailMessage.getLink()), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(ErrorCode.EMAIL_SEND_ERROR);
        }
    }

    private String getHtmlText(String content, String link) {
        return "<div>\n" +
                "    <xlink href=\"https://fonts.googleapis.com/css?family=Noto+Sans+KR\" rel=\"stylesheet\">\n" +
                "        <table style=\"width: 100%; height: 600px;\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" width=\"400px\" style=\"background-color: #f5f5f5;\">\n" +
                "                        <div style=\"text-align: left; width: 400px; min-height: 300px;border-radius: 12px; background-color: #ffffff;box-sizing: border-box; padding: 36px 40px 36px 40px;font-family: 'Noto Sans KR', sans-serif;font-size: 13px;color: rgba(0, 0, 0, 0.54);user-select: none;\">\n" +
                "                            <div style=\"padding-bottom: 25px; text-align: center; font-size: 30px; font-weight: bold; color: #06B563;\">\n" +
                "                                COALBA\n" +
                "                            </div>\n" +
                "                            <div style=\"width: 320px; height: auto; word-break: break-all; word-wrap: break-word; font-size: 24px; font-weight: 700;line-height: 36px; padding-bottom: 16px;color: rgba(0, 0, 0, 0.82);margin: 0 auto;\">\n" +
                "                                " + content + "\n" +
                "                            </div>\n" +
                "                            <div style=\"width : 100%; height: auto; word-break: break-all; padding-bottom: 24px;\">\n" +
                "                                <span style=\"font-weight: 500;\">\n" +
                "                                    초대를 수락하여 워크스페이스 멤버가 되어 주세요.\n" +
                "                                </span>\n" +
                "                            </div>\n" +
                "                            <a href=\"" + link + "\"\n" +
                "                               style=\": 100%;: 40px;text-align: center;color: #ffffff;text-decoration: none; display: inline-block;\" rel=\"noreferrer noopener\" target=\"_blank\">\n" +
                "                                <div style=\"width: 320px;background-color: #06B563;border: solid 1px #22CC88;border-radius: 5px;display: inline-block;padding-top: 12px; padding-bottom: 10px;font-size: 14px;font-weight: 500;line-height: 1.43;text-align: center;color: #ffffff;text-decoration: none; cursor: pointer;\">\n" +
                "                                    초대 수락하기\n" +
                "                                </div>\n" +
                "                            </a>\n" +
                "                        </div>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </xlink>\n" +
                "</div>";
    }
}
