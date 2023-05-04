# 예외 처리

## 예외 Response-Body

> **요청 데이터 형식 에러**

{% tabs %}
{% tab title="Schema" %}
| Name              | Type      | Description              |
| ----------------- | --------- | ------------------------ |
| `timestamp`       | `String`  | api 요청 시간                |
| `status`          | `Integer` | 응답 상태 코드                 |
| `error`           | `String`  | 응답 상태 코드 이름              |
| `code`            | `String`  | 커스텀 에러 코드                |
| `message`         | `String`  | 커스텀 에러 메시지               |
| `defaultMessages` | `Array`   | 요청 형식이 잘못된 필드에 대한 에러 메시지 |


{% endtab %}

{% tab title="Example" %}
```json
{
    "timestamp": "2023-02-11T23:00:32.0697009",
    "status": 400,
    "error": "BAD_REQUEST",
    "code": "INVALID_REQUEST_FIELD",
    "message": "유효하지 않은 요청 필드입니다.",
    "defaultMessages": [
        "\"010[0-9]{8}\"와 일치해야 합니다",
        "공백일 수 없습니다"
    ]
}
```
{% endtab %}
{% endtabs %}



> **서비스 내부 에러**

{% tabs %}
{% tab title="Schema" %}
| Name        | Type      | Description |
| ----------- | --------- | ----------- |
| `timestamp` | `String`  | api 요청 시간   |
| `status`    | `Integer` | 응답 상태 코드    |
| `error`     | `Strin`   | 응답 상태 코드 이름 |
| `code`      | `String`  | 커스텀 에러 코드   |
| `message`   | `String`  | 커스텀 에러 메시지  |
{% endtab %}

{% tab title="Example" %}
```json
{
    "timestamp": "2023-02-11T22:52:42.4837756",
    "status": 400,
    "error": "BAD_REQUEST",
    "code": "LATE_SCHEDULE_START",
    "message": "해당 스케줄이 종료되어 출근 불가합니다."
}
```
{% endtab %}
{% endtabs %}





## 예외 목록

### 도메인 관련 예외

#### 📌 auth

| 예외 설명                                         | 에러 코드 이름                                                | 상태 코드        | 에러 코드 메시지                                               |
| --------------------------------------------- | ------------------------------------------------------- | ------------ | ------------------------------------------------------- |
| 해당 유저가 없는 경우                                  | USER\_NOT\_FOUND                                        | NOT\_FOUND   | 해당 유저를 찾을 수 없습니다.                                       |
| 토큰 재발급 시 access token이 유효하지 않은 경우 (by. 토큰 변조) | INVALID\_ACCESS\_TOKEN                                  | BAD\_REQUEST | access token이 변조되어 유효하지 않습니다.                           |
| 토큰 재발급 시 해당 user의 refresh token이 없는 경우        | REFRESH\_TOKEN\_NOT\_FOUND                              | NOT\_FOUND   | 해당 유저의 refresh token이 없습니다. 재로그인을 진행합니다.                |
| 토큰 재발급 시 refresh token이 유효하지 않은 경우            | INVALID\_REFRESH\_TOKEN                                 | BAD\_REQUEST | refresh token이 일치하지 않습니다. 재로그인을 진행합니다.                  |
| 인증되지 않은 유저가 인증이 필요한 api 호출하는 경우               | (controller 전 filter에서 발생하는 에러로 global handler에서 처리 불가) | UNAUTHORIZED | Full authentication is required to access this resource |



#### 📌 profile

| 예외 설명                       | 에러 코드 이름                                 | 상태 코드      | 에러 코드 메시지                            |
| --------------------------- | ---------------------------------------- | ---------- | ------------------------------------ |
| 현재 로그인한 이용자의 프로필이 없는 경우     | PROFILE\_NOT\_FOUND                      | NOT\_FOUND | 현재 프로필이 등록되어 있지 않습니다. 프로필 등록을 진행합니다. |
| 해당 staff 프로필이 없는 경우         | STAFF\_PROFILE\_NOT\_FOUND\_BY\_ID       | NOT\_FOUND | 해당 알바생이 없습니다.                        |
| 해당 이메일을 가진 staff 프로필이 없는 경우 | STAFF\_PROFILE\_NOT\_FOUND\_BY\_EMAIL    | NOT\_FOUND | 해당 이메일을 가진 알바생이 없습니다.                |
| 해당 워크스페이스의 boss 프로필이 없는 경우  | BOSS\_PROFILE\_NOT\_FOUND\_BY\_WORKSPACE | NOT\_FOUND | 해당 워크스페이스의 사장님이 없습니다.                |



#### 📌 workspace

| 예외 설명                                        | 에러 코드 이름                             | 상태 코드        | 에러 코드 메시지                    |
| -------------------------------------------- | ------------------------------------ | ------------ | ---------------------------- |
| 해당 워크스페이스가 없는 경우                             | WORKSPACE\_NOT\_FOUND                | NOT\_FOUND   | 해당 워크스페이스가 없습니다.             |
| 이미 존재하는 워크스페이스와 사업자 번호가 같은 워크스페이스 생성 요청하는 경우 | ALREADY\_EXIST\_WORKSPACE            | BAD\_REQUEST | 해당 사업자 번호의 워크스페이스가 이미 존재합니다. |
| 해당 워크스페이스의 멤버인 알바를 다시 초대하는 경우                | ALREADY\_EXIST\_STAFF\_IN\_WORKSPACE | BAD\_REQUEST | 해당 알바는 이미 해당 워크스페이스의 멤버입니다.  |



#### 📌 schedule

| 예외 설명                                          | 에러 코드 이름                  | 상태 코드        | 에러 코드 메시지                                 |
| ---------------------------------------------- | ------------------------- | ------------ | ----------------------------------------- |
| 해당 스케줄이 없는 경우                                  | SCHEDULE\_NOT\_FOUND      | NOT\_FOUND   | 해당 스케줄이 없습니다.                             |
| 해당 알바가 해당 스케줄 날짜시간에 다른 스케줄이 이미 존재하는 경우         | INVALID\_SCHEDULE\_WORKER | BAD\_REQUEST | 해당 알바는 해당 스케줄 시간에 근무 불가합니다. 다른 알바를 선택합니다. |
| 스케줄 상태가 BEFORE\_WORK가 아닌 스케줄에 대해 취소 요청한 경우     | INVALID\_SCHEDULE\_CANCEL | BAD\_REQUEST | 스케줄 시작 전인 경우에만 취소 가능합니다.                  |
| (스케줄 시작 날짜시간 - 10분) 보다 전에 해당 스케줄에 대해 출근 요청한 경우 | EARLY\_SCHEDULE\_START    | BAD\_REQUEST | 해당 스케줄 시작 10분 전부터 출근 가능합니다.               |
| 스케줄 종료 날짜시간 이후에 해당 스케줄에 대해 출근 요청한 경우           | LATE\_SCHEDULE\_START     | BAD\_REQUEST | 해당 스케줄이 종료되어 출근 불가합니다.                    |
| 해당 스케줄에 대해 출근하지 않은 상태로 퇴근 요청한 경우               | INVALID\_SCHEDULE\_END    | BAD\_REQUEST | 출근 상태일 때에만 퇴근 가능합니다.                      |



#### 📌 substituteReq

| 예외 설명                                | 에러 코드 이름                  | 상태 코드        | 에러 코드 메시지                      |
| ------------------------------------ | ------------------------- | ------------ | ------------------------------ |
| 해당 id의 대타근무 요청건이 없는 경우               | SUBSTITUTEREQ\_NOT\_FOUND | NOT\_FOUND   | 해당 대타근무 요청건을 찾을 수 없습니다.        |
| 이미 상대방에 의해 수락 혹은 거절된 대타근무 요청을 취소할 경우 | ALREADY\_PROCESSED\_REQ   | BAD\_REQUEST | 이미 수락 혹은 거절된 요청이므로 취소할 수 없습니다. |



#### 📌 notification

| 예외 설명                        | 에러 코드 이름                 | 상태 코드      | 에러 코드 메시지                               |
| ---------------------------- | ------------------------ | ---------- | --------------------------------------- |
| 해당 유저의 알림을 위한 디바이스 토큰이 없는 경우 | NOTIFICATION\_NOT\_FOUND | NOT\_FOUND | 해당 유저의 알림정보를 찾을 수 없습니다. 디바이스 토큰을 등록합니다. |



#### 📌 invitation

| 예외 설명                                 | 에러 코드 이름                          | 상태 코드        | 에러 코드 메시지          |
| ------------------------------------- | --------------------------------- | ------------ | ------------------ |
| 해당 알바에게 보낸 유효한 워크스페이스 초대장이 이미 존재하는 경우 | ALREADY\_EXIST\_VALID\_INVITATION | BAD\_REQUEST | 유효한 초대장이 이미 존재합니다. |
| 해당 초대장이 없는 경우                         | INVITATION\_NOT\_FOUND            | NOT\_FOUND   | 해당 초대장을 찾을 수 없습니다. |
| 해당 초대장이 만료된 경우                        | EXPIRED\_INVITATION\_LINK         | BAD\_REQUEST | 만료된 초대 링크입니다.      |



#### 📌 공통

| 예외 설명                                              | 에러 코드 이름                | 상태 코드        | 에러 코드 메시지         |
| -------------------------------------------------- | ----------------------- | ------------ | ----------------- |
| Boss가 Staff API 요청하는 경우 또는 Staff가 Boss API 요청하는 경우 |                         | FORBIDDEN    | Forbidden         |
| DTO validation을 만족하지 않는 요청이 들어온 경우                 | INVALID\_REQUEST\_FIELD | BAD\_REQUEST | 유효하지 않은 요청 필드입니다. |





### 외부 API 관련 예외

#### 📌 social login

| 예외 설명                            | 에러 코드 이름               | 상태 코드                   | 에러 코드 메시지         |
| -------------------------------- | ---------------------- | ----------------------- | ----------------- |
| 로그인 시 프론트로부터 전달받은 토큰이 유효하지 않은 경우 | INVALID\_SOCIAL\_TOKEN | INTERNAL\_SERVER\_ERROR | 유효하지 않은 소셜 토큰입니다. |



#### 📌 mail

| 예외 설명                    | 에러 코드 이름           | 상태 코드                   | 에러 코드 메시지        |
| ------------------------ | ------------------ | ----------------------- | ---------------- |
| 해당 이메일에 대한 메일 발송에 실패한 경우 | EMAIL\_SEND\_ERROR | INTERNAL\_SERVER\_ERROR | 이메일 발송에 실패하였습니다. |



#### 📌 google calendar oauth

| 예외 설명                   | 에러 코드 이름                   | 상태 코드        | 에러 코드 메시지           |
| ----------------------- | -------------------------- | ------------ | ------------------- |
| 구글 캘린더에 대한 접근 권한이 없는 경우 | NOPERMISSION\_TO\_CALENDAR | UNAUTHORIZED | 해당 캘린더에 접근할 수 없습니다. |



#### 📌 aws s3

| 예외 설명                      | 에러 코드 이름                        | 상태 코드                   | 에러 코드 메시지                                         |
| -------------------------- | ------------------------------- | ----------------------- | ------------------------------------------------- |
| aws s3에 파일을 업로드하지 못한 경우    | FILE\_UPLOAD\_ERROR             | INTERNAL\_SERVER\_ERROR | 파일 업로드에 실패하였습니다.                                  |
| 업로드 요청 파일 이름에 확장자가 없는 경우   | INVALID\_FILE\_NAME\_FORM       | BAD\_REQUEST            | 잘못된 형식의 파일 이름입니다. 파일 이름에 확장자가 포함되어야 합니다.          |
| 업로드 요청 파일 확장자가 유효하지 않는 경우  | INVALID\_IMAGE\_FILE\_EXTENSION | BAD\_REQUEST            | 잘못된 이미지 파일 확장자입니다. .png, .jpg, .jpeg 중 하나이어야 합니다. |
| 삭제 요청 파일 URL에 파일 이름이 없는 경우 | INVALID\_FILE\_URL\_FORM        | BAD\_REQUEST            | 잘못된 형식의 파일 URL입니다. / 문자 이후에 파일 이름이 포함되어야 합니다.     |

