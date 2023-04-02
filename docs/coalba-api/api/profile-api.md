---
description: 프로필 관련 API
---

# Profile API

## <mark style="color:green;">GET</mark> /boss/profile

> **내 프로필 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name          | Type     | Description |
| ------------- | -------- | ----------- |
| `realName`    | `String` | 이름          |
| `phoneNumber` | `String` | 핸드폰 번호      |
| `birthDate`   | `String` | 생년월일        |
| `imageUrl`    | `String` | 프로필 이미지 url |
{% endtab %}

{% tab title="Example" %}
```json
{
    "realName": "김다은",
    "phoneNumber": "01012345678",
    "birthDate": "1999-12-24",
    "imageUrl": "http://"
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:yellow;">POST</mark> /boss/profile

> **내 프로필 등록**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Request-Body

{% tabs %}
{% tab title="Schema" %}
| Name                                            | Type            | Description |
| ----------------------------------------------- | --------------- | ----------- |
| <mark style="color:red;">\*</mark>`profile`     | `Object`        | 프로필 등록 데이터  |
| <mark style="color:red;">\*</mark>`realName`    | `String`        | 이름          |
| <mark style="color:red;">\*</mark>`phoneNumber` | `String`        | 전화번호        |
| <mark style="color:red;">\*</mark>`birthDate`   | `String`        | 생년월일        |
| `imageFile`                                     | `MultiPartFile` | 프로필 이미지 파일  |
{% endtab %}

{% tab title="Example" %}
```json
{
    "profile": {
        "realName": "김나은",
        "phoneNumber": "01087654321",
        "birthDate": "1999-12-25"
    },
    "imageFile": "${이미지 파일}"
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:blue;">PUT</mark> /boss/profile

> #### 내 프로필 수정

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Request-Body

{% tabs %}
{% tab title="Schema" %}
| Name                                            | Type            | Description      |
| ----------------------------------------------- | --------------- | ---------------- |
| <mark style="color:red;">\*</mark>`profile`     | `Object`        | 프로필 수정 데이터       |
| <mark style="color:red;">\*</mark>`realName`    | `String`        | 이름               |
| <mark style="color:red;">\*</mark>`phoneNumber` | `String`        | 전화번호             |
| <mark style="color:red;">\*</mark>`birthDate`   | `String`        | 생년월일             |
| `prevImageUrl`                                  | `String`        | 수정 전 프로필 이미지 url |
| `imageFile`                                     | `MultiPartFile` | 프로필 이미지 파일       |
{% endtab %}

{% tab title="Example" %}
```json
{
    "profile": {
        "realName": "김나은",
        "phoneNumber": "01087654321",
        "birthDate": "1999-12-25",
        "prevImageUrl": "https://coalba.abc/imageFile.png"
    },
    "imageFile": "${이미지 파일}"
}
```
{% endtab %}
{% endtabs %}

