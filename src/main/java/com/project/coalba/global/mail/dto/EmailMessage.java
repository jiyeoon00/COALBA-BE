package com.project.coalba.global.mail.dto;

import lombok.*;

@Getter @Builder
public class EmailMessage {
    private String to; //수신자 이메일
    private String subject; //제목
    private String text; //내용
    private boolean isHtmlText; //내용이 html 템플릿인지 여부
}
