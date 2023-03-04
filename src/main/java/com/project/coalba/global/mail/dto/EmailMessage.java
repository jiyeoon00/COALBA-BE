package com.project.coalba.global.mail.dto;

import lombok.*;

@Getter @Builder
public class EmailMessage {
    private String to; //수신자 이메일
    private String subject; //제목
    private String content; //내용
    private String invitationLink; //초대 링크
}
