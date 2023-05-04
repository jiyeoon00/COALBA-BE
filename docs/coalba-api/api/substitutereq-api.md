---
description: 대타요청 관련 API
---

# SubstituteReq API

### 📌 대타근무 요청건 상태 Enum

**상황** : 알바생A → 알바생B 대타근무 요청

* `WAITING` 알바생A가 요청 후 대기
* `ACCEPTANCE` 알바생B가 요청 수락
* `REFUSAL` 알바생B가 요청 거절
* `APPROVAL` 사장님이 근무 교환 승인 (알바생A 🔄️ 알바생B)
* `DISAPPROVAL` 사장님이 근무 교환 비승인
* `CANCELLATION` 알바생A가 요청 취소

<mark style="color:green;"></mark>

<mark style="color:green;"></mark>

## <mark style="color:green;">GET</mark> /boss/substituteReqs

> **대타근무 요청 관리 리스트 조회 (월 별)**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name                     | Type      | Description        |
| ------------------------ | --------- | ------------------ |
| `totalSubstituteReqList` | `List`    | 전체 대타 요청 리스트       |
| `year`                   | `Integer` | 년도                 |
| `month`                  | `Integer` | 월                  |
| `substituteReqList`      | `List`    | 월별 대타 요청 리스트       |
| `substituteReqId`        | `Long`    | 대타 요청 고유 id        |
| `senderId`               | `Long`    | 요청 발신자 고유 id       |
| `senderName`             | `String`  | 요청 발신자 이름          |
| `senderImageUrl`         | `String`  | 요청 발신자 프로필 이미지 url |
| `receiverId`             | `Long`    | 요청 수신자 고유 id       |
| `receiverName`           | `String`  | 요청 수신자 이름          |
| `receiverImageUrl`       | `String`  | 요청 수신자 프로필 이미지 url |
| `workspaceId`            | `Long`    | 요청 워크스페이스 고유 id    |
| `workspaceName`          | `String`  | 요청 워크스페이스 이름       |
| `startDateTime`          | `String`  | 요청 스케줄 시작 날짜 시간    |
| `endDateTime`            | `String`  | 요청 스케줄 종료 날짜 시간    |
| `status`                 | `String`  | 요청 상태              |
{% endtab %}

{% tab title="Example" %}
```json
{
    "totalSubstituteReqList": [
        {
	    "year": 2022,
	    "month": 9,
	    "substituteReqList": [
	        {
		    "substituteReqId": 5,
                    "senderId": 2,
                    "senderImageUrl": "http://abcd",
                    "senderName": "김다은",
                    "receiverId": 1,
                    "receiverImageUrl": "http://abcd",
                    "receiverName": "조예진",
                    "workspaceId": 1,
                    "workspaceName": "가게1",
                    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
                    "status": "ACCEPTANCE"
		},
		{
		    "substituteReqId": 5,
                    "senderId": 2,
                    "senderImageUrl": "http://abcd",
                    "senderName": "신지연",
                    "receiverId": 1,
                    "receiverImageUrl": "http://abcd",
                    "receiverName": "조예진",
                    "workspaceId": 1,
                    "workspaceName": "가게1",
                    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
                    "status": "ACCEPTANCE"
		}
	    ]
	},
	{
	    "year": 2022,
	    "month": 8,
	    "substituteReqList": [
	        {
		    "substituteReqId": 5,
                    "senderId": 2,
                    "senderImageUrl": "http://abcd",
                    "senderName": "김다은",
                    "receiverId": 1,
                    "receiverImageUrl": "http://abcd",
                    "receiverName": "조예진",
                    "workspaceId": 1,
                    "workspaceName": "가게1",
                    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
                    "status": "ACCEPTANCE"
		}
	    ]
	}
    ]
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:green;">GET</mark> **** /boss/substituteReqs/{substituteReqId}

> **대타근무 요청 관리 상세 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name               | Type     | Description        |
| ------------------ | -------- | ------------------ |
| `substituteReqId`  | `Long`   | 대타 요청 고유 id        |
| `reqMessage`       | `String` | 요청 메시지             |
| `status`           | `String` | 요청 상태              |
| `senderId`         | `Long`   | 요청 발신자 고유 id       |
| `senderName`       | `String` | 요청 발신자 이름          |
| `senderImageUrl`   | `String` | 요청 발신자 프로필 이미지 url |
| `receiverId`       | `Long`   | 요청 수신자 고유 id       |
| `receiverName`     | `String` | 요청 수신자 이름          |
| `receiverImageUrl` | `String` | 요청 수신자 프로필 이미지 url |
| `workspaceId`      | `Long`   | 요청 워크스페이스 고유 id    |
| `workspaceName`    | `String` | 요청 워크스페이스 이름       |
| `startDateTime`    | `String` | 요청 스케줄 시작 날짜 시간    |
| `endDateTime`      | `String` | 요청 스케줄 종료 날짜 시간    |
{% endtab %}

{% tab title="Example" %}
```json
{
    "substituteReqId": 1,
    "reqMessage": "이날 갑자기 학교 시험 일정이 잡혀버려서요... 대타 부탁드립니다!!",
    "status": "APPROVAL",
    "senderId": 1,
    "senderName": "김다은",
    "senderImageUrl": "http://abcd",
    "receiverId": 2,
    "receiverName": "신지연",
    "receiverImageUrl": "http://abcd",
    "workspaceId": 1,
    "workspaceName": "가게1",
    "startDateTime": "11/18 17:00",
    "endDateTime": "11/18 22:00"
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:blue;">PUT</mark> /boss/substituteReqs/{substituteReqId}/accept

> **대타근무 요청 승인**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |





## <mark style="color:blue;">**PUT**</mark>** ** /boss/substituteReqs/{substituteReqId}/reject

> **대타근무 요청 비승인**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

