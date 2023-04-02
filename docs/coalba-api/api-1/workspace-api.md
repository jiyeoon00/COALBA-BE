---
description: 워크스페이스 관련 API
---

# Workspace API

## <mark style="color:green;">GET</mark> /staff/workspaces

> **나의 워크스페이스 리스트 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name            | Type     | Description    |
| --------------- | -------- | -------------- |
| `workspaceList` | `List`   | 나의 워크스페이스 리스트  |
| `workspaceId`   | `Long`   | 워크스페이스 고유 id   |
| `name`          | `String` | 워크스페이스 이름      |
| `imageUrl`      | `String` | 워크스페이스 이미지 url |

* 나의 워크스페이스가 없다면 `workspaceList`는 빈 리스트
{% endtab %}

{% tab title="Example" %}
```json
{
    "workspaceList": [
        {
            "workspaceId": 2,
            "name": "송이커피 숙대점",
            "imageUrl": "http://"
        },
        {
            "workspaceId": 1,
            "name": "송이마라탕 숙대점",
            "imageUrl": "http://"
        }
    ]
}
```

```json
{
    "workspaceList": []
}
```
{% endtab %}
{% endtabs %}

