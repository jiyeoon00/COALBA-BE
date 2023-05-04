---
description: 대타요청 관련 API
---

# SubstituteReq API

## <mark style="color:green;">GET</mark> /staff/substituteReqs/possible/staffs

> **대타근무 요청 가능한 알바 리스트 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Params

| Name                                           | Description  |
| ---------------------------------------------- | ------------ |
| <mark style="color:red;">\*</mark>`scheduleId` | 해당 스케줄 고유 id |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name        | Type     | Description      |
| ----------- | -------- | ---------------- |
| `staffList` | `List`   | 대타 요청 가능한 알바 리스트 |
| `staffId`   | `Long`   | 알바 고유 id         |
| `name`      | `String` | 알바 이름            |
| `imageUrl`  | `String` | 알바 프로필 이미지 url   |
{% endtab %}

{% tab title="Example" %}
```json
{
    "staffList": [
        {
            "staffId": 5,
	    "name": "김다은",
            "imageUrl": "http://"
	},
	{
	    "staffId": 4,
	    "name": "신지연",
            "imageUrl": "http://"
	}
    ]
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:green;">**GET**</mark>** ** /staff/substituteReqs/from

> **내가 보낸 대타근무 요청 관리 리스트 조회(from. 나)**

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
		    "substituteReqId": 4,
		    "receiverId": 3, 
                    "receiverName": "신지연",
		    "receiverImageUrl": "http://",
		    "workspaceId": 1,
		    "workspaceName": "송이커피 숙대점",
		    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
		    "status": "WAITING"
		},
		{
		    "substituteReqId": 5,
		    "receiverId": 1, 
                    "receiverName": "신지연",
		    "receiverImageUrl": "http://",
		    "workspaceId": 1,
		    "workspaceName": "송이커피 숙대점",
		    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
		    "status": "APPROVAL"
		},
		{
		    "substituteReqId": 7,
		    "receiverId": 3, 
                    "receiverName": "신지연",
                    "receiverImageUrl": "http://",
                    "workspaceId": 1,
                    "workspaceName": "송이마라탕탕 숙대점",
                    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
                    "status": "WAITING"
                }
            ]
        },
        {
            "year": 2022,
            "month": 8,
            "substituteReqList": [
                {
                    "substituteReqId": 4,
                    "receiverId": 3, 
                    "receiverName": "신지연",
                    "receiverImageUrl": "http://",
                    "workspaceId": 1,
                    "workspaceName": "송이커피 숙대점",
                    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
                    "status": "WAITING" 
		}
            ]
        }
    ]
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:green;">GET</mark> /staff/substituteReqs/to

> **내가 받은 대타근무 요청 관리 리스트 조회(to. 나)**

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
		    "substituteReqId": 8,
                    "senderId": 1,
                    "senderName": "알바1",
                    "senderImageUrl": "http://abcd",
                    "workspaceId": 1,
                    "workspaceName": "가게1",
                    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
                    "status": "WAITING"
		},
		{
		    "substituteReqId": 8,
                    "senderId": 1,
                    "senderName": "알바1",
                    "senderImageUrl": "http://abcd",
                    "workspaceId": 1,
                    "workspaceName": "가게1",
                    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
                    "status": "WAITING"
		},
		{
		    "substituteReqId": 8,
                    "senderId": 1,
                    "senderName": "알바1",
                    "senderImageUrl": "http://abcd",
                    "workspaceId": 1,
                    "workspaceName": "가게1",
                    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
                    "status": "WAITING"
		}
	    ]
	},
	{
	    "year": 2022,
	    "month": 8,
	    "substituteReqList": [
	        {
		    "substituteReqId": 8,
                    "senderId": 1,
                    "senderName": "알바1",
                    "senderImageUrl": "http://abcd",
                    "workspaceId": 1,
                    "workspaceName": "가게1",
                    "startDateTime": "11/18 23:55",
                    "endDateTime": "11/18 23:55",
                    "status": "WAITING"
		}
	    ]
	}
    ]
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:green;">GET</mark> /staff/substituteReqs/{substituteReqId}

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
    "workspaceName": "송이마라탕",
    "startDateTime": "11/18 17:00",
    "endDateTime": "11/18 22:00"
}
```
{% endtab %}
{% endtabs %}



****

## <mark style="color:yellow;">**POST**</mark>** ** /staff/substituteReqs

> **대타근무 요청 생성 (나의 스케줄인 경우에만)**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Params

| Name                                           | Description  |
| ---------------------------------------------- | ------------ |
| <mark style="color:red;">\*</mark>`scheduleId` | 해당 스케줄 고유 id |
| <mark style="color:red;">\*</mark>`receiverId` | 요청 수신자 고유 id |

**Request-Body**

{% tabs %}
{% tab title="Schema" %}
| Name                                           | Type     | Description |
| ---------------------------------------------- | -------- | ----------- |
| <mark style="color:red;">\*</mark>`reqMessage` | `String` | 요청 메시지      |
{% endtab %}

{% tab title="Example" %}
```json
{
    "reqMessage": "이날 갑자기 학교 시험 일정이 잡혀버려서요... 대타 부탁드립니다!!"
}
```
{% endtab %}
{% endtabs %}

****

****

## <mark style="color:blue;">**PUT**</mark>** ** /staff/substituteReqs/{substituteReqId}/cancel

> **대타근무 요청 취소 (from 나, 상대가 아직 수락 또는 거절하지 않은 경우에만)**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |





## <mark style="color:blue;">PUT</mark> /staff/substituteReqs/{substituteReqId}/accept

> **대타근무 요청 수락**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

****

****

## <mark style="color:blue;">PUT</mark> /staff/substituteReqs/{substituteReqId}/reject

> **대타근무 요청 거절**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

