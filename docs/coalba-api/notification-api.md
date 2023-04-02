---
description: 알림 관련 API
---

# 공통 Notification API

## <mark style="color:yellow;">POST</mark> /notification

> 디바이스 토큰 등록 (앱 시작 시마다 요청)

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Request-Body

{% tabs %}
{% tab title="Schema" %}
| Name                                            | Type     | Description      |
| ----------------------------------------------- | -------- | ---------------- |
| <mark style="color:red;">\*</mark>`deviceToken` | `String` | 알림 기능 위한 디바이스 토큰 |
{% endtab %}

{% tab title="Example" %}
```json
{
    "deviceToken" : "dddddddfsfdsfsadaa5f46sf15a6d5s1f56a"
}
```
{% endtab %}
{% endtabs %}

