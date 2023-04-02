---
description: 워크스페이스 관련 API
---

# Workspace API

### 📌 스케줄 변동 유형 Enum

* `FIXED_WORK` **고정 근무**, 스케줄 변동되지 않고 고정
* `MONTHLY_WORK` **월간 근무**, 스케줄이 월마다 변동
* `WEEKLY_WORK` **주간 근무**, 스케줄이 주마다 변동

### 📌 알바비 지급 유형 Enum

* `MONTHLY_PAY` **월급**
* `WEEKLY_PAY` **주급**

<mark style="color:green;"></mark>

<mark style="color:green;"></mark>

## <mark style="color:green;">GET</mark> /boss/workspaces

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
<pre class="language-json"><code class="lang-json"><strong>{
</strong>    "workspaceList": [
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
</code></pre>

```json
{
    "workspaceList": []
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:green;">GET</mark> /boss/workspaces/brief

> **나의 워크스페이스 요약 리스트 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name            | Type     | Description   |
| --------------- | -------- | ------------- |
| `workspaceList` | `List`   | 나의 워크스페이스 리스트 |
| `workspaceId`   | `Long`   | 워크스페이스 고유 id  |
| `name`          | `String` | 워크스페이스 이름     |

* 나의 워크스페이스가 없다면 `workspaceList`는 빈 리스트
{% endtab %}

{% tab title="Example" %}
```json
{
    "workspaceList": [
        {
            "workspaceId": 2,
            "name": "송이커피 숙대점"
        },
        {
            "workspaceId": 1,
            "name": "송이마라탕 숙대점"
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





## <mark style="color:green;">GET</mark> /boss/workspaces/{workspaceId}

> **해당 워크스페이스 정보 상세 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name             | Type     | Description    |
| ---------------- | -------- | -------------- |
| `workspaceId`    | `Long`   | 워크스페이스 고유 id   |
| `name`           | `String` | 워크스페이스 이름      |
| `phoneNumber`    | `String` | 워크스페이스 전화번호    |
| `address`        | `String` | 워크스페이스 주소      |
| `businessNumber` | `String` | 워크스페이스 사업자 번호  |
| `workType`       | `String` | 근무 유형          |
| `payType`        | `String` | 알바비 지급 유형      |
| `imageUrl`       | `String` | 워크스페이스 이미지 url |
{% endtab %}

{% tab title="Example" %}
```json

{
    "workspaceId": 1,
    "name": "송이커피 숙대점",
    "phoneNumber": "021234567",
    "address": "서울특별시 용산구 청파동", 
    "businessNumber": "0123456789",
    "workType": "MONTHLY_WORK",
    "payType": "MONTHLY_PAY",
    "imageUrl": "http://"
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:green;">GET</mark> /boss/workspaces/{workspaceId}/staffs

> **해당 워크스페이스 내 알바 정보 리스트 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name            | Type      | Description    |
| --------------- | --------- | -------------- |
| `staffInfoList` | `List`    | 알바 정보 리스트      |
| `staffId`       | `Long`    | 알바 고유 id       |
| `name`          | `String`  | 알바 이름          |
| `phoneNumber`   | `String`  | 알바 전화번호        |
| `birthDate`     | `String`  | 알바 생년월일        |
| `imageUrl`      | `String`  | 알바 프로필 이미지 url |
| `workGrade`     | `Integer` | 알바 근무 평점       |

* 해당 워크스페이스 내 알바생이 없다면 `staffInfoList`는 빈 리스트
{% endtab %}

{% tab title="Example" %}
```json
{
    "staffInfoList": [
        {
            "staffId": 4,
            "name": "조예진",
            "phoneNumber": "01012345678",
            "birthDate": "1999-12-25",
            "imageUrl": "http://",
            "workGrade": 89
        },
        {
            "staffId": 3,
            "name": "신지연",
            "phoneNumber": "01012345678",
            "birthDate": "1999-12-25",
            "imageUrl": "http://",
            "workGrade": 89
        },
        {
            "staffId": 5,
            "name": "김다은",
            "phoneNumber": "01012345678",
            "birthDate": "1999-12-25",
            "imageUrl": "http://",
            "workGrade": 89
        }
    ]
}
```

```json
{
    "staffInfoList": []
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:yellow;">POST</mark> /boss/workspaces/{workspaceId}/invite

> **해당 워크스페이스에 알바 초대**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Params

| Name                                              | Description   |
| ------------------------------------------------- | ------------- |
| <mark style="color:red;">\*</mark>`receiverEmail` | 초대 수신자 고유 이메일 |

****

****

## <mark style="color:yellow;">**POST**</mark>** ** /boss/workspaces

> **워크스페이스 추가**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Request-Body

{% tabs %}
{% tab title="Schema" %}
| Name                                               | Type            | Description   |
| -------------------------------------------------- | --------------- | ------------- |
| <mark style="color:red;">\*</mark>`workspace`      | `Object`        | 워크스페이스 등록 데이터 |
| <mark style="color:red;">\*</mark>`name`           | `String`        | 워크스페이스 이름     |
| <mark style="color:red;">\*</mark>`phoneNumber`    | `String`        | 워크스페이스 전화번호   |
| <mark style="color:red;">\*</mark>`address`        | `String`        | 워크스페이스 주소     |
| <mark style="color:red;">\*</mark>`businessNumber` | `String`        | 워크스페이스 사업자 번호 |
| <mark style="color:red;">\*</mark>`workType`       | `String`        | 근무 유형         |
| <mark style="color:red;">\*</mark>`payType`        | `String`        | 알바비 지급 유형     |
| `imageFile`                                        | `MultipartFile` | 워크스페이스 이미지 파일 |
{% endtab %}

{% tab title="Example" %}
```json
{
    "workspace": {
        "name": "송이토스트 숙대점",
        "phoneNumber": "021112222",
        "address": "서울특별시 용산구 청파2동",
        "businessNumber": "0000000000",
        "workType": "MONTHLY_WORK",
        "payType": "MONTHLY_PAY"
    },
    "imageFile": "${이미지 파일}"
}
```
{% endtab %}
{% endtabs %}

#### **Response-Body**

{% tabs %}
{% tab title="Schema" %}
| Name            | Type     | Description    |
| --------------- | -------- | -------------- |
| `workspaceList` | `List`   | 나의 워크스페이스 리스트  |
| `workspaceId`   | `Long`   | 워크스페이스 고유 id   |
| `name`          | `String` | 워크스페이스 이름      |
| `imageUrl`      | `String` | 워크스페이스 이미지 url |

* 나의 워크스페이스 전체 리스트 반환 (추가된 워크스페이스 포함)
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
{% endtab %}
{% endtabs %}

****

****

## <mark style="color:blue;">PUT</mark> /boss/workspaces/{workspaceId}

> **해당 워크스페이스 정보 수정**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Request-Body

{% tabs %}
{% tab title="Schema" %}
| Name                                            | Type            | Description         |
| ----------------------------------------------- | --------------- | ------------------- |
| <mark style="color:red;">\*</mark>`workspace`   | `Object`        | 워크스페이스 수정 데이터       |
| <mark style="color:red;">\*</mark>`name`        | `String`        | 워크스페이스 이름           |
| <mark style="color:red;">\*</mark>`phoneNumber` | `String`        | 워크스페이스 전화번호         |
| <mark style="color:red;">\*</mark>`address`     | `String`        | 워크스페이스 주소           |
| `prevImageUrl`                                  | `String`        | 수정 전 워크스페이스 이미지 url |
| `imageFile`                                     | `MultipartFile` | 워크스페이스 이미지 파일       |
{% endtab %}

{% tab title="Example" %}
```json
{
    "workspace": {
        "name": "송이토스트 숙대점",
        "phoneNumber": "021112222",
        "address": "서울특별시 용산구 청파2동",
        "prevImageUrl": "http://coalba.abc/workspace.png"
    },
    "imageFile": "${이미지 파일}"
}
```
{% endtab %}
{% endtabs %}

