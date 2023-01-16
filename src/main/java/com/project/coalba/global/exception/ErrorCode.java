package com.project.coalba.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //auth
    INVALID_SOCIAL_TOKEN(INTERNAL_SERVER_ERROR, "유효하지 않은 소셜 토큰입니다."),
    INVALID_ACCESS_TOKEN(BAD_REQUEST, "access token이 변조되어 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "해당 유저의 refresh token이 없습니다. 재로그인을 진행합니다."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "refresh token이 일치하지 않습니다. 재로그인을 진행합니다."),

    // NOT_FOUND : Resource를 찾을 수 없음
    SUBSTITUTEREQ_NOT_FOUND(NOT_FOUND, "해당 대타근무 요청건을 찾을 수 없습니다."),

    // BAD_REQEST : 잘못된 요청
    ALREADY_PROCESSED_REQ(BAD_REQUEST, "이미 수락 혹은 거절된 요청이므로 취소할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
