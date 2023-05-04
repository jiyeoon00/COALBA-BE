---
description: 쪽지 관련 API
---

# Message API

## <mark style="color:green;">GET</mark> /boss/messages/boxes

> **해당 워크스페이스 내 알바와의 쪽지함 리스트 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Params

| Name                                            | Description     |
| ----------------------------------------------- | --------------- |
| <mark style="color:red;">\*</mark>`workspaceId` | 해당 워크스페이스 고유 id |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name              | Type     | Description        |
| ----------------- | -------- | ------------------ |
| `messageBoxList`  | `List`   | 해당 워크스페이스의 쪽지함 리스트 |
| `staff`           | `Object` | 해당 쪽지함의 상대 알바 정보   |
| `staffId`         | `Long`   | 알바 고유 id           |
| `name`            | `String` | 알바 이름              |
| `ImageUrl`        | `String` | 알바 프로필 이미지 url     |
| `lastestMessage`  | `String` | 해당 쪽지함의 최근 메세지     |
| `lastestDateTime` | `String` | 해당 쪽지함의 최근 업데이트 시간 |
{% endtab %}

{% tab title="Example" %}
```json
{
    "messageBoxList": [
        {
            "staff": {
                "staffId": 1,
                "name": "김다은",
                "imageUrl": "http://"
            },
            "latestMessage": "네:)",
            "latestDateTime": "2023-01-11 13:47"
        },
        {
            "staff": {
                "staffId": 2,
                "name": "신지연",
                "imageUrl": "http://abcd"
            },
            "latestMessage": "",
            "latestDateTime": null
        }
    ]
}
```
{% endtab %}
{% endtabs %}



<mark style="color:green;"></mark>

## <mark style="color:green;">GET</mark> /boss/messages

> **해당 워크스페이스의 해당 알바 쪽지함 내 메시지 리스트 조회 (최신순)**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Params

| Name                                            | Description     |
| ----------------------------------------------- | --------------- |
| <mark style="color:red;">\*</mark>`workspaceId` | 해당 워크스페이스 고유 id |
| <mark style="color:red;">\*</mark>`staffId`     | 해당 알바 고유 id     |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name                 | Type     | Description          |
| -------------------- | -------- | -------------------- |
| `workspaceId`        | `Long`   | 워크스페이스 고유 id         |
| `staffId`            | `Long`   | 알바 고유 id             |
| `staffName`          | `String` | 알바 이름                |
| `staffImageUrl`      | `String` | 알바 프로필 이미지 url       |
| `messageList`        | `List`   | 해당 쪽지함 내 메시지 리스트     |
| `messageId`          | `Long`   | 메세지 고유 id            |
| `sendingOrReceiving` | `String` | `"보낸쪽지"` or `"받은쪽지"` |
| `content`            | `String` | 메세지 내용               |
| `createDate`         | `String` | 메세지 생성 시간            |
{% endtab %}

{% tab title="Example" %}
```json
{
    "workspaceId": 2,
    "staffId": 4,
    "staffName": "조예진",
    "staffImageUrl": "http://",
    "messageList": [
	{
            "messagId": 4, 
            "sendingOrReceiving": "보낸쪽지",
            "content": "어쩌구저쩌구어쩌구저쩌구",
            "createDate": "09/28 13:10"
        },
        {
            "messagId": 3, 
            "sendingOrReceiving": "받은쪽지",
            "content": "어쩌구저쩌구어쩌구저쩌구",
            "createDate": "09/28 12:59"
        },
        {
            "messagId": 2, 
            "sendingOrReceiving": "보낸쪽지",
            "content": "어쩌구저쩌구어쩌구저쩌구",
            "createDate": "09/28 12:50"
        },
        {
            "messagId": 1, 
            "sendingOrReceiving": "받은쪽지",
            "content": "어쩌구저쩌구어쩌구저쩌구"
            "createDate": "09/28 12:44"
        }
    ]
}
```
{% endtab %}
{% endtabs %}



<mark style="color:yellow;"></mark>

## <mark style="color:yellow;">POST</mark> /boss/messages

> **해당 워크스페이스의 해당 알바에게 쪽지 보내기**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Params

| Name                                            | Description     |
| ----------------------------------------------- | --------------- |
| <mark style="color:red;">\*</mark>`workspaceId` | 해당 워크스페이스 고유 id |
| <mark style="color:red;">\*</mark>`staffId`     | 해당 알바 고유 id     |

#### Request-Body

{% tabs %}
{% tab title="Schema" %}
| Name                                        | Type     | Description |
| ------------------------------------------- | -------- | ----------- |
| <mark style="color:red;">\*</mark>`content` | `String` | 메시지 내용      |
{% endtab %}

{% tab title="Example" %}
```json
{
    "content": "안녕하세요. 잘 부탁드립니다~"
}
```
{% endtab %}
{% endtabs %}

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name                 | Type     | Description          |
| -------------------- | -------- | -------------------- |
| `workspaceId`        | `Long`   | 워크스페이스 고유 id         |
| `staffId`            | `Long`   | 알바 고유 id             |
| `staffName`          | `String` | 알바 이름                |
| `staffImageUrl`      | `String` | 알바 프로필 이미지 url       |
| `messageList`        | `List`   | 해당 쪽지함 내 메시지 리스트     |
| `messageId`          | `Long`   | 메세지 고유 id            |
| `sendingOrReceiving` | `String` | `"보낸쪽지"` or `"받은쪽지"` |
| `content`            | `String` | 메세지 내용               |
| `createDate`         | `String` | 메세지 생성 시간            |

* 해당 워크스페이스의 해당 알바 쪽지함 내 메시지 전체 리스트 반환 (보낸 메시지 포함)
{% endtab %}

{% tab title="Example" %}
```json
{
    "workspaceId": 2,
    "staffId": 4,
    "staffName": "조예진",
    "staffImageUrl": "http://",
    "messageList": [
	{
            "messagId": 4, 
            "sendingOrReceiving": "보낸쪽지",
            "content": "어쩌구저쩌구어쩌구저쩌구",
            "createDate": "09/28 13:10"
        },
        {
            "messagId": 3, 
            "sendingOrReceiving": "받은쪽지",
            "content": "어쩌구저쩌구어쩌구저쩌구",
            "createDate": "09/28 12:59"
        },
        {
            "messagId": 2, 
            "sendingOrReceiving": "보낸쪽지",
            "content": "어쩌구저쩌구어쩌구저쩌구",
            "createDate": "09/28 12:50"
        },
        {
            "messagId": 1, 
            "sendingOrReceiving": "받은쪽지",
            "content": "어쩌구저쩌구어쩌구저쩌구"
            "createDate": "09/28 12:44"
        }
    ]
}
```
{% endtab %}
{% endtabs %}

