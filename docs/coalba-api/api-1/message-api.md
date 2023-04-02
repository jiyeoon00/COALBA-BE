---
description: 쪽지 관련 API
---

# Message API

## <mark style="color:green;">GET</mark> /staff/messages

> **해당 워크스페이스 쪽지함 내 메시지 리스트 조회 (최신순)**

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
| Name                 | Type     | Description          |
| -------------------- | -------- | -------------------- |
| `workspaceId`        | `Long`   | 워크스페이스 고유 id         |
| `workspaceName`      | `String` | 워크스페이스 이름            |
| `workspaceImageUrl`  | `String` | 워크스페이스 이미지 url       |
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
    "workspaceName": "송이커피 숙대점",
    "workspaceImageUrl": "http://",
    "messageList": [
	{      
	    "messageId": 4, 
	    "sendingOrReceiving": "받은쪽지",
	    "content": "어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구",
	    "createDate": "09/28 13:10"
	},
	{
	    "messageId": 3,
	    "sendingOrReceiving": "보낸쪽지",
	    "content": "어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구",
	    "createDate": "09/28 12:59"
	},
	{
	    "messageId": 2,
	    "sendingOrReceiving": "받은쪽지",
	    "content": "어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구",
	    "createDate": "09/28 12:50"
	},
	{
	    "messageId": 1,
	    "sendingOrReceiving": "보낸쪽지",
	    "content": "어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구",
	    "createDate": "09/28 12:44"
	}
    ]
}
```
{% endtab %}
{% endtabs %}



****

## <mark style="color:yellow;">POST</mark> <mark style="color:blue;"></mark> /staff/messages

> **해당 워크스페이스에 쪽지 보내기**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Params

| Name                                            | Description     |
| ----------------------------------------------- | --------------- |
| <mark style="color:red;">\*</mark>`workspaceId` | 해당 워크스페이스 고유 id |

#### Request-Body

{% tabs %}
{% tab title="Schema" %}
| Name                                        | Description |
| ------------------------------------------- | ----------- |
| <mark style="color:red;">\*</mark>`content` | 메시지 내용      |
{% endtab %}

{% tab title="Example" %}
```json
{
    "content": "안녕하세요 사장님 앞으로 잘부탁드립니다."
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
| `workspaceName`      | `String` | 워크스페이스 이름            |
| `workspaceImageUrl`  | `String` | 워크스페이스 이미지 url       |
| `messageList`        | `List`   | 해당 쪽지함 내 메시지 리스트     |
| `messageId`          | `Long`   | 메세지 고유 id            |
| `sendingOrReceiving` | `String` | `"보낸쪽지"` or `"받은쪽지"` |
| `content`            | `String` | 메세지 내용               |
| `createDate`         | `String` | 메세지 생성 시간            |

* 해당 워크스페이스 쪽지함 내 메시지 전체 리스트 반환 (보낸 메시지 포함)
{% endtab %}

{% tab title="Example" %}
```json
{
    "workspaceId": 2,
    "workspaceName": "송이커피 숙대점",
    "workspaceImageUrl": "http://",
    "messageList": [
	{      
	    "messageId": 4, 
	    "sendingOrReceiving": "받은쪽지",
	    "content": "어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구",
	    "createDate": "09/28 13:10"
	},
	{
	    "messageId": 3,
	    "sendingOrReceiving": "보낸쪽지",
	    "content": "어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구",
	    "createDate": "09/28 12:59"
	},
	{
	    "messageId": 2,
	    "sendingOrReceiving": "받은쪽지",
	    "content": "어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구",
	    "createDate": "09/28 12:50"
	},
	{
	    "messageId": 1,
	    "sendingOrReceiving": "보낸쪽지",
	    "content": "어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구",
	    "createDate": "09/28 12:44"
	}
    ]
}
```
{% endtab %}
{% endtabs %}

