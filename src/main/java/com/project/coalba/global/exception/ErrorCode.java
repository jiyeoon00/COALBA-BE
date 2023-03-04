package com.project.coalba.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //auth
    USER_NOT_FOUND(NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    INVALID_SOCIAL_TOKEN(INTERNAL_SERVER_ERROR, "유효하지 않은 소셜 토큰입니다."),
    INVALID_ACCESS_TOKEN(BAD_REQUEST, "access token이 변조되어 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "해당 유저의 refresh token이 없습니다. 재로그인을 진행합니다."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "refresh token이 일치하지 않습니다. 재로그인을 진행합니다."),

    //profile
    PROFILE_NOT_FOUND(NOT_FOUND, "현재 프로필이 등록되어 있지 않습니다. 프로필 등록을 진행합니다."),
    STAFF_PROFILE_NOT_FOUND_BY_ID(NOT_FOUND, "해당 알바생이 없습니다."),
    STAFF_PROFILE_NOT_FOUND_BY_EMAIL(NOT_FOUND, "해당 이메일을 가진 알바생이 없습니다."),
    BOSS_PROFILE_NOT_FOUND_BY_WORKSPACE(NOT_FOUND, "해당 워크스페이스의 사장님이 없습니다."),

    //workspace
    WORKSPACE_NOT_FOUND(NOT_FOUND, "해당 워크스페이스가 없습니다."),
    ALREADY_EXIST_WORKSPACE(BAD_REQUEST, "해당 사업자 번호의 워크스페이스가 이미 존재합니다."),
    ALREADY_EXIST_STAFF_IN_WORKSPACE(BAD_REQUEST, "해당 알바는 이미 해당 워크스페이스의 멤버입니다."),

    //schedule
    SCHEDULE_NOT_FOUND(NOT_FOUND, "해당 스케줄이 없습니다."),
    INVALID_SCHEDULE_WORKER(BAD_REQUEST, "해당 알바는 해당 스케줄 시간에 근무 불가합니다. 다른 알바를 선택합니다."),
    INVALID_SCHEDULE_CANCEL(BAD_REQUEST, "스케줄 시작 전인 경우에만 취소 가능합니다."),
    EARLY_SCHEDULE_START(BAD_REQUEST, "해당 스케줄 시작 10분 전부터 출근 가능합니다."),
    LATE_SCHEDULE_START(BAD_REQUEST, "해당 스케줄이 종료되어 출근 불가합니다."),
    INVALID_SCHEDULE_END(BAD_REQUEST, "출근 상태일 때에만 퇴근 가능합니다."),

    //substituteReq : Resource를 찾을 수 없음
    SUBSTITUTEREQ_NOT_FOUND(NOT_FOUND, "해당 대타근무 요청건을 찾을 수 없습니다."),
    ALREADY_PROCESSED_REQ(BAD_REQUEST, "이미 수락 혹은 거절된 요청이므로 취소할 수 없습니다."),

    //notification
    NOTIFICATION_NOT_FOUND(NOT_FOUND, "해당 유저의 알림정보를 찾을 수 없습니다. 디바이스 토큰을 등록합니다."),

    //invitation
    INVITATION_NOT_FOUND(NOT_FOUND, "해당 초대장을 찾을 수 없습니다."),
    EXPIRED_INVITATION_LINK(BAD_REQUEST, "만료된 초대 링크입니다."),

    //mail
    EMAIL_SEND_ERROR(INTERNAL_SERVER_ERROR, "이메일 발송에 실패하였습니다."),

    //googleCalendar Oauth
    NOPERMISSION_TO_CALENDAR(UNAUTHORIZED, "해당 캘린더에 접근할 수 없습니다."),

    //DTO validation
    INVALID_REQUEST_FIELD(BAD_REQUEST, "유효하지 않은 요청 필드입니다."),

    //Aws S3
    FILE_UPLOAD_ERROR(INTERNAL_SERVER_ERROR, "파일 업로드에 실패하였습니다."),
    INVALID_FILE_NAME_FORM(BAD_REQUEST, "잘못된 형식의 파일 이름입니다. 파일 이름에 확장자가 포함되어야 합니다."),
    INVALID_FILE_URL_FORM(BAD_REQUEST, "잘못된 형식의 파일 URL입니다. / 문자 이후에 파일 이름이 포함되어야 합니다."),
    INVALID_IMAGE_FILE_EXTENSION(BAD_REQUEST, "잘못된 이미지 파일 확장자입니다. .png, .jpg, .jpeg 중 하나이어야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
