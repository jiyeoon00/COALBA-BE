---
description: 스케줄 관련 API
---

# Schedule API

### 📌 스케줄 상태 Enum

* `BEFORE_WORK` 스케줄 시작 전
* `ON_DUTY` 스케줄 진행 중 (정시 출근 후)
* `LATE` 스케줄 진행 중 (지각 출근 후)
* `SUCCESS` 스케줄 완료
* `FAIL` 스케줄 미완료 (ex. 지각, 조기 퇴근, 결근 등)

### 📌 전체 스케줄 상태 Enum

* `COMPLETE` 해당 날짜의 스케줄 모두 완료 (스케줄 상태가 모두 SUCCESS)
* `INCOMPLETE` 해당 날짜의 스케줄 중 완료되지 않은 건 1건 이상 존재
* `BEFORE` 해당 날짜의 스케줄 모두 시작 전 (현재 날짜 이후 날짜)
* `NONE` 해당 날짜의 스케줄 존재X

<mark style="color:green;"></mark>

<mark style="color:green;"></mark>

## <mark style="color:green;">GET</mark> /staff/schedules/home

> **홈 달력 정보 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name                   | Type      | Description                              |
| ---------------------- | --------- | ---------------------------------------- |
| `dateList`             | `List`    | 오늘 날짜 기준 ±3일별 날짜와 전체 스케줄 상태 리스트          |
| `date`                 | `Object`  | 일별 날짜 정보                                 |
| `year`                 | `Integer` | 년도                                       |
| `month`                | `Integer` | 월                                        |
| `day`                  | `Integer` | 일                                        |
| `dayOfWeek`            | `String`  | 요일 이름                                    |
| `totalScheduleStatus`  | `String`  | 해당 날짜 전체 스케줄 상태                          |
| `selectedSubPage`      | `Object`  | 조회 일, 조회된 스케줄 리스트 정보 (날짜 선택 시마다 내용 업데이트) |
| `selectedDate`         | `Object`  | 오늘 날짜 정보 (선택된 날짜 정보)                     |
| `selectedScheduleList` | `List`    | 조회된 스케줄 리스트                              |
| `scheduleId`           | `Long`    | 스케줄 id                                   |
| `scheduleStartTime`    | `String`  | 예정된 스케줄 시작 시간                            |
| `scheduleEndTime`      | `String`  | 예정된 스케줄 종료 시간                            |
| `logicalStartTime`     | `String`  | 논리적 스케줄 시작 시간                            |
| `logicalEndTime`       | `String`  | 논리적 스케줄 종료 시간                            |
| `status`               | `String`  | 현재 스케줄 상태                                |
| `workspace`            | `Object`  | 워크스페이스 정보                                |
| `workspaceId`          | `Long`    | 워크스페이스 id                                |
| `name`                 | `String`  | 워크스페이스 이름                                |

#### ✅ 논리적 스케줄 시작/종료 시간?

* 스케줄 출퇴근 시 현재 시간을 10분 단위로, 논리적으로 계산한 시간&#x20;
* ex. 10시가 출근 시간일 때, 10시 12분에 출근 시 10시 10분으로 출근 시간 저장&#x20;
* ex. 17시가 퇴근 시간일 때, 16시 37분에 퇴근 시 16시 30분으로 퇴근 시간 저장
* ex. 17시가 퇴근 시간일 때, 20시에 퇴근 시 17시로 퇴근 시간 저장&#x20;

✅ `logicalStartTime`이 null이면 `scheduleStartTime`, `logicalEndTime`이 null이면 `scheduleEndTime`을 표시
{% endtab %}

{% tab title="Example" %}
```json
{
    "dateList": [
        {
            "date": {
                "year": 2022, 
                "month": 9,
                "day": 27,
                "dayOfWeek": "TUESDAY"
            }, 
            "totalScheduleStatus": "COMPLETE"
	},
	{
	    "date": {
                "year": 2022,
                "month": 9,
                "day": 28,
                "dayOfWeek": "WEDNESDAY"
            },
	    "totalScheduleStatus": "INCOMPLETE"
	},
	{
	    "date": {
                "year": 2022,
                "month": 9,
                "day": 29,
                "dayOfWeek": "THURSDAY"
            },
	    "totalScheduleStatus": "NONE"
	},
	{
	    "date": {
                "year": 2022,
                "month": 9,
                "day": 30,
                "dayOfWeek": "FRIDAY"
            },
	    "totalScheduleStatus": "INCOMPLETE"
	},
	{
	    "date": {
                "year": 2022,
                "month": 10,
                "day": 1,
                "dayOfWeek": "SATURDAY"
            },
	    "totalScheduleStatus": "NONE"
	},
	{
	    "date":{
                "year": 2022,
                "month": 10,
                "day": 2,
                "dayOfWeek": "SUNDAY"
            },
	    "totalScheduleStatus": "BEFORE"
	},
	{
	    "date": {
                "year": 2022,
                "month": 10,
                "day": 3,
                "dayOfWeek": "MONDAY"
            },
	    "totalScheduleStatus": "BEFORE"
	}
    ],

    "selectedSubPage": {
        "selectedDate": {
            "year": 2022,
            "month": 9,
            "day": 30,
            "dayOfWeek": "FRIDAY"
        },
        "selectedScheduleList": [
            {
                "scheduleId": 3,
                "scheduleStartTime": "09:00",
                "scheduleEndTime": "12:00",
                "logicalStartTime": "09:10",
                "logicalEndTime": "12:00", 
                "status": "LATE",
                "workspace": {
                    "workspaceId": 1,
                    "name": "송이커피 숙대점"
                }
            },
            {
                "scheduleId": 2,
                "scheduleStartTime": "13:00",
                "scheduleEndTime": "17:00",
                "logicalStartTime": "13:00",
                "logicalEndTime": null,
                "status": "ING",
                "workspace": {
                    "workspaceId": 2, 
                    "name": "송이마라탕 숙대점"
                }
            },
            {
                "scheduleId": 1,
                "scheduleStartTime": "21:00",
                "scheduleEndTime": "03:00",
                "logicalStartTime": null,
                "logicalEndTime": null,
                "status": "BEFORE",
                "workspace": {
                    "workspaceId": 3, 
                    "name": "송이샐러드 숙대점"
                }
            }
        ]
    }
}
```
{% endtab %}
{% endtabs %}

****

****

## <mark style="color:green;">**GET**</mark>** ** /staff/schedules/home/selected

> **홈 해당 날짜 스케줄 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Params

| Name                                      | Description |
| ----------------------------------------- | ----------- |
| <mark style="color:red;">\*</mark>`year`  | 조회 년도       |
| <mark style="color:red;">\*</mark>`month` | 조회 월        |
| <mark style="color:red;">\*</mark>`day`   | 조회 일        |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name                   | Type      | Description          |
| ---------------------- | --------- | -------------------- |
| `selectedDate`         | `Object`  | 오늘 날짜 정보 (선택된 날짜 정보) |
| `year`                 | `Integer` | 년도                   |
| `month`                | `Integer` | 월                    |
| `day`                  | `Integer` | 일                    |
| `dayOfWeek`            | `String`  | 요일 이름                |
| `selectedScheduleList` | `List`    | 조회된 스케줄 리스트          |
| `scheduleId`           | `Long`    | 스케줄 id               |
| `scheduleStartTime`    | `String`  | 예정된 스케줄 시작 시간        |
| `scheduleEndTime`      | `String`  | 예정된 스케줄 종료 시간        |
| `logicalStartTime`     | `String`  | 논리적 스케줄 시작 시간        |
| `logicalEndTime`       | `String`  | 논리적 스케줄 종료 시간        |
| `status`               | `String`  | 현재 스케줄 상태            |
| `workspace`            | `Object`  | 워크스페이스 정보            |
| `workspaceId`          | `Long`    | 워크스페이스 id            |
| `name`                 | `String`  | 워크스페이스 이름            |
{% endtab %}

{% tab title="Example" %}
```json
{
    "selectedDate": {
        "year": 2022,
        "month": 9,
        "day": 30,
        "dayOfWeek": "FRIDAY"
    },
    "selectedScheduleList": [
        {
            "scheduleId": 3,
            "scheduleStartTime": "09:00",
            "scheduleEndTime": "12:00",
            "logicalStartTime": "09:10",
            "logicalEndTime": "12:00", 
            "status": "LATE",
            "workspace": {
                "workspaceId": 1,
                "name": "송이커피 숙대점"
            }
        },
        {
            "scheduleId": 2,
            "scheduleStartTime": "13:00",
            "scheduleEndTime": "17:00",
            "logicalStartTime": "13:00",
            "logicalEndTime": null,
            "status": "ING",
            "workspace": {
                "workspaceId": 2, 
                "name": "송이마라탕 숙대점"
            }
        },
        {
            "scheduleId": 1,
            "scheduleStartTime": "21:00",
            "scheduleEndTime": "03:00",
            "logicalStartTime": null,
            "logicalEndTime": null,
            "status": "BEFORE",
            "workspace": {
                "workspaceId": 3, 
                "name": "송이샐러드 숙대점"
            }
        }
    ]
}
```

```json
{
    "selectedDate": {
        "year": 2022,
        "month": 9,
        "day": 30,
        "dayOfWeek": "FRIDAY"
    },
    "selectedScheduleList": []
}
```
{% endtab %}
{% endtabs %}

****

****

## <mark style="color:green;">**GET**</mark>** ** /staff/schedules

> **해당 워크스페이스 홈 달력 정보 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Params

| Name                                            | Description  |
| ----------------------------------------------- | ------------ |
| <mark style="color:red;">\*</mark>`workspaceId` | 조회 워크스페이스 id |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name                   | Type      | Description                              |
| ---------------------- | --------- | ---------------------------------------- |
| `selectedWorkspace`    | `Object`  | 조회 워크스페이스 정보                             |
| `workspaceId`          | `Long`    | 워크스페이스 id                                |
| `name`                 | `String`  | 워크스페이스 이름                                |
| `imageUrl`             | `String`  | 워크스페이스 이미지 url                           |
| `year`                 | `Integer` | 현재 년도                                    |
| `month`                | `Integer` | 현재 월                                     |
| `dateList`             | `List`    | 현재 년월의 일 리스트                             |
| `day`                  | `Integer` | 일                                        |
| `dayOfWeek`            | `String`  | 요일 이름                                    |
| `isMySchedule`         | `Boolean` | 일별 내 스케줄 포함 여부                           |
| `selectedSubPage`      | `Object`  | 조회 일, 조회된 스케줄 리스트 정보 (날짜 선택 시마다 내용 업데이트) |
| `selectedDay`          | `Integer` | 조회 일                                     |
| `selectedScheduleList` | `List`    | 조회된 스케줄 리스트                              |
| `scheduleId`           | `Long`    | 스케줄 id                                   |
| `scheduleStartTime`    | `String`  | 예정된 스케줄 시작 시간                            |
| `scheduleEndTime`      | `String`  | 예정된 스케줄 종료 시간                            |
| `status`               | `String`  | 현재 스케줄 상태                                |
| `worker`               | `Object`  | 스케줄 근무자 정보                               |
| `workerId`             | `Long`    | 근무자 id                                   |
| `name`                 | `String`  | 근무자 이름                                   |
| `isMySchedule`         | `Boolean` | 해당 스케줄 내 스케줄인지 여부                        |
{% endtab %}

{% tab title="Example" %}
```json
{
    "selectedWorkspace": {
        "workspaceId": 1,
        "name": "송이커피 숙대점", 
        "imageUrl": "http://"
    },
    "year": 2022,
    "month": 9,
    "dateList": [
	{
	    "day": 1,
            "dayOfWeek": "THURSDAY",
	    "isMySchedule": false
	},
	{
	    "day": 2,
            "dayOfWeek": "FRIDAY",
	    "isMySchedule": true
	},
        //......
	{
	    "day": 29,
            "dayOfWeek": "THURSDAY",
	    "isMySchedule": false
	},
	{
	    "day": 30,
            "dayOfWeek": "FRIDAY",
	    "isMySchedule": true
	}
    ],
    
    "selectedSubPage": {
        "selectedDay": 30,
        "selectedScheduleList": [
            {
                "scheduleId": 10,
                "scheduleStartTime": "15:00",
                "scheduleEndTime": "19:00",
                "status": "LATE",
                "worker": {
                    "workerId": 3,
                    "name": "신지연"
                },
                "isMySchedule": true
            },
            {
                "scheduleId": 9,
                "scheduleStartTime": "17:00",
                "scheduleEndTime": "19:00",
                "status": "ON_DUTY",
                "worker": {
                    "workerId": 5,
                    "name": "김다은"
                },
                "isMySchedule": false
            },
            {
                "scheduleId": 8,
                "scheduleStartTime": "22:00",
                "scheduleEndTime": "03:00",
                "status": "BEFORE_WORK",
                "worker": {
                    "workerId": 4,
                    "name": "조예진"
                },
                "isMySchedule": false
            }
        ]
    }
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:green;">GET</mark> /staff/schedules/selected

> **해당 워크스페이스 홈 해당 날짜 스케줄 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Params

| Name                                            | Description  |
| ----------------------------------------------- | ------------ |
| <mark style="color:red;">\*</mark>`workspaceId` | 조회 워크스페이스 id |
| <mark style="color:red;">\*</mark>`year`        | 조회 년도        |
| <mark style="color:red;">\*</mark>`month`       | 조회 월         |
| <mark style="color:red;">\*</mark>`day`         | 조회 일         |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name                   | Type      | Description       |
| ---------------------- | --------- | ----------------- |
| `selectedDay`          | `Integer` | 조회 일              |
| `selectedScheduleList` | `List`    | 조회된 스케줄 리스트       |
| `scheduleId`           | `Long`    | 스케줄 id            |
| `scheduleStartTime`    | `String`  | 예정된 스케줄 시작 시간     |
| `scheduleEndTime`      | `String`  | 예정된 스케줄 종료 시간     |
| `status`               | `String`  | 현재 스케줄 상태         |
| `worker`               | `Object`  | 스케줄 근무자 정보        |
| `workerId`             | `Long`    | 근무자 id            |
| `name`                 | `String`  | 근무자 이름            |
| `isMySchedule`         | `Boolean` | 해당 스케줄 내 스케줄인지 여부 |
{% endtab %}

{% tab title="Example" %}
```json
{
    "selectedDay": 30,
    "selectedScheduleList": [
        {
            "scheduleId": 10,
            "scheduleStartTime": "15:00",
            "scheduleEndTime": "19:00",
            "status": "LATE",
            "worker": {
                "workerId": 3,
                "name": "신지연"
            },
            "isMySchedule": true
        },
        {
            "scheduleId": 9,
            "scheduleStartTime": "17:00",
            "scheduleEndTime": "19:00",
            "status": "ON_DUTY",
            "worker": {
                "workerId": 5,
                "name": "김다은"
            },
            "isMySchedule": false
        },
        {
            "scheduleId": 8,
            "scheduleStartTime": "22:00",
            "scheduleEndTime": "03:00",
            "status": "BEFORE_WORK",
            "worker": {
                "workerId": 4,
                "name": "조예진"
            },
            "isMySchedule": false
        }
    ]
}
```
{% endtab %}
{% endtabs %}



<mark style="color:blue;"></mark>

## <mark style="color:green;">GET</mark> /staff/schedules/{scheduleId}

> **해당 스케줄 요약 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name                | Type     | Description |
| ------------------- | -------- | ----------- |
| `scheduleId`        | `Long`   | 조회스케줄 id    |
| `scheduleDate`      | `String` | 스케줄 날짜      |
| `scheduleStartTime` | `String` | 스케줄 시작 시간   |
| `scheduleEndTime`   | `String` | 스케줄 종료 시간   |
| `workspaceName`     | `String` | 근무지 이름      |
{% endtab %}

{% tab title="Example" %}
```json
{
    "scheduleId": 1,
    "scheduleDate": "2022-09-25",
    "scheduleStartTime": "21:00",
    "scheduleEndTime": "03:00",
    "workspaceName": "송이샐러드 숙대점"
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:blue;">PUT</mark> <mark style="color:orange;"></mark> /staff/schedules/{scheduleId}/start

> **해당 스케줄 출근 요청**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name               | Type     | Description   |
| ------------------ | -------- | ------------- |
| `scheduleId`       | `Long`   | 해당 스케줄 id     |
| `logicalStartTime` | `String` | 논리적 스케줄 시작 시간 |
| `status`           | `String` | 현재 스케줄 상태     |

* 해당 api 요청 후 홈 화면에서 해당 id의 스케줄 시작 시간, 상태값 업데이트 필요
{% endtab %}

{% tab title="Example" %}
```json
{
    "scheduleId": 22,
    "logicalStartTime": "02:00",
    "status": "ON_DUTY"
}
```
{% endtab %}
{% endtabs %}





## <mark style="color:blue;">PUT</mark> <mark style="color:red;"></mark> /staff/schedules/{scheduleId}/end

> **해당 스케줄 퇴근 요청**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body&#x20;

{% tabs %}
{% tab title="Schema" %}
| Name             | Type     | Description   |
| ---------------- | -------- | ------------- |
| `scheduleId`     | `Long`   | 해당 스케줄 id     |
| `logicalEndTime` | `String` | 논리적 스케줄 종료 시간 |
| `status`         | `String` | 현재 스케줄 상태     |

* 해당 api 요청 후 홈 화면에서 해당 id의 스케줄 종료 시간, 상태값 업데이트 필요
{% endtab %}

{% tab title="Example" %}
```json
{
    "scheduleId": 22,
    "logicalEndTime": "02:20",
    "status": "FAIL"
}
```
{% endtab %}
{% endtabs %}

****

****

## <mark style="color:green;">GET</mark> /staff/schedules/reports/dates

> **근무내역 및 알바비 관리 년도 리스트 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### Response-Body

{% tabs %}
{% tab title="Schema" %}
| Name       | Type   | Description                 |
| ---------- | ------ | --------------------------- |
| `yearList` | `List` | 근무내역 및 알바비 관리 조회 가능한 년도 리스트 |
{% endtab %}

{% tab title="Example" %}
```json
{
    "yearList": [
        2021, 
        2022,
        2023
    ]
}
```
{% endtab %}
{% endtabs %}

****

****

## <mark style="color:green;">GET</mark> /staff/schedules/reports

> **해당 년도 근무내역 및 알바비 관리 리스트 조회**

#### Request-Header

| Name                                             | Type     | Description    |
| ------------------------------------------------ | -------- | -------------- |
| <mark style="color:red;">\*</mark>`x-auth-token` | `String` | user jwt token |

#### **Params**

| Name                                     | Description |
| ---------------------------------------- | ----------- |
| <mark style="color:red;">\*</mark>`year` | 조회 년도       |

#### **Response-Body**

{% tabs %}
{% tab title="First Tab" %}
| Name                | Type      | Description       |
| ------------------- | --------- | ----------------- |
| `selectedYear`      | `Integer` | 조회 년도             |
| `workReportList`    | `List`    | 근무내역 및 알바비 관리 리스트 |
| `month`             | `Integer` | 조회 월              |
| `totalWorkTimeHour` | `Integer` | 해당 월 전체 근무 시간     |
| `totalWorkTimeMin`  | `Integer` | 해당 월 전체 근무 분      |
| `totalWorkPay`      | `String`  | 해당 월 전체 알바비       |
{% endtab %}

{% tab title="Second Tab" %}
```json
{
    "selectedYear": 2022, 
    "workReportList": [
    	{
    	    "month": 9,
            "totalWorkTimeHour": 35,
            "totalWorkTimeMin": 30,
            "totalWorkPay": "320,600"
	},
	{
	    "month": 8,
	    "totalWorkTimeHour": 35,
	    "totalWorkTimeMin": 30,
	    "totalWorkPay": "320,600"
	}
    ]
}
```
{% endtab %}
{% endtabs %}

****
