---
description: 인증 관련 API
---

# 공통 Auth API

## <mark style="color:yellow;">POST</mark> /auth/login

> **token 발급**

#### Params

| Name                                         | Value             | Description   |
| -------------------------------------------- | ----------------- | ------------- |
| <mark style="color:red;">\*</mark>`provider` | `GOOGLE`, `NAVER` | 소셜 로그인 제공 서비스 |
| <mark style="color:red;">\*</mark>`role`     | `STAFF`, `BOSS`   | 알바 or 사장 여부   |

#### Request-Body

{% tabs %}
{% tab title="Schema" %}
| Name                                                   | Type     | Description       |
| ------------------------------------------------------ | -------- | ----------------- |
| <mark style="color:red;">\*</mark>`socialAccessToken`  | `String` | 구글 or 네이버 엑세스 토큰  |
| <mark style="color:red;">\*</mark>`socialRefreshToken` | `String` | 구글 or 네이버 리프레시 토큰 |
{% endtab %}

{% tab title="Example" %}
```json
{
    "socialAccessToken": "asdfasdf", //구글 or 네이버 access token
    "socialRefreshToken": "asdfafd"  //구글 or 네이버 refresh token
}
```
{% endtab %}
{% endtabs %}

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name           | Type      | Description       |
| -------------- | --------- | ----------------- |
| `accessToken`  | `String`  | 엑세스 토큰 (유효기간 30분) |
| `refreshToken` | `String`  | 리프레시 토큰 (유효기간 7일) |
| `isNewUser`    | `Boolean` | 새로 가입한 유저인지 여부    |

* 새로운 유저 or 기존 유저이면서 refresh token 없거나 있는데 만료 기간이 2일 이하인 경우에만 새로운 refresh token 발급&#x20;
* 기존 유저이면서 refresh token 만료 기간이 2일 넘으면 기존 refresh token 발급&#x20;
{% endtab %}

{% tab title="Example" %}
```json
{
    "accessToken": "asdfasdf", //coalba 서버 access token
    "refreshToken": "asdfafd", //coalba 서버 refresh token
    "isNewUser": false
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:yellow;">POST</mark> /auth/refresh

> **refresh token으로 token 재발급**

#### Request-Body

{% tabs %}
{% tab title="Schema" %}
| Name                                             | Type     | Description |
| ------------------------------------------------ | -------- | ----------- |
| <mark style="color:red;">\*</mark>`accessToken`  | `String` | 만료된 엑세스 토큰  |
| <mark style="color:red;">\*</mark>`refreshToken` | `String` | 유효한 리프레시 토큰 |
{% endtab %}

{% tab title="Example" %}
```json
{
    "accessToken": "asdfasdf", 
    "refreshToken": "asdfasdf"
}
```
{% endtab %}
{% endtabs %}

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name           | Type     | Description       |
| -------------- | -------- | ----------------- |
| `accessToken`  | `String` | 엑세스 토큰 (유효기간 30분) |
| `refreshToken` | `String` | 리프레시 토큰 (유효기간 7일) |

* refresh token 만료 기간이 2일 이하인 경우에만 새로운 refresh token 발급&#x20;
* refresh token 만료 기간이 2일 넘는 경우에는 기존 refresh token 발급
{% endtab %}

{% tab title="Example" %}
```json
{
    "accessToken": "asdfasdf", //coalba 서버 access token
    "refreshToken": "asdfafd", //coalba 서버 refresh token
}
```
{% endtab %}
{% endtabs %}

