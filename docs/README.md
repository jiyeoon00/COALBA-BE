<img src="https://github.com/COALBA-Graduation-Project/COALBA-BE/assets/65665065/1d02429d-906e-4ed1-ae45-410e7e8577e4" width="55%" height="55%"/>

## 기획 의도
- **잦은 알바 스케줄 변동** ➡️ 스케줄에 변동이 생긴 경우 여러 공지된 시간표로 인해 최종 스케줄을 파악하기 어려움 
- **번거로운 대타요청** ➡️ 대타요청 시 다른 알바생, 사장님에게 여러 번 연락해야 하며, 요청 성사 시 사장님은 스케줄 근무자를 변경하고 재공지해야 함
- **다양한 서비스 필요** ➡️ 근태관리, 급여관리 등 아르바이트에 필요한, 다양한 서비스를 한 곳에서 이용하고 싶음
<br/>

## 프로젝트 소개
> COALBA에서는 사장님과 알바생이 함께하는 공간 워크스페이스에서 공유된 스케줄표를 확인할 수 있으며 보다 편리한 대타요청으로 
요청 승인 시 자동으로 스케줄 근무자가 변경됩니다. 또한 비콘 기반 근태관리와 이에 따른 급여확인까지 오직 한 앱에서 이용 가능합니다.

### 와이어 프레임
https://www.figma.com/file/aTjPtnx8RpnVNdWtYYAlhN/COALBA?type=design&node-id=0%3A1&t=hm0PxlM7ESIQF6OW-1
<br/>

### API 문서
https://coalba.gitbook.io/coalba-api/
<br/>

### DB 설계
![image](https://github.com/COALBA-Graduation-Project/.github/assets/65665065/a644a6be-18b4-40d5-8f2b-25abd5cba228)
<br/>

### 개발 환경 및 구조
<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/47990b44-672c-43ca-aa44-5205c4b55107" width="70%" height="70%"/>
<br/>

### 사장 앱 기능 플로우
<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/d7d34231-d470-4c9c-9299-eb5e946c7ae9" width="55%" height="55%"/>

### 알바 앱 기능 플로우
<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/28ac3313-db8d-428c-b886-7744c8e014e3" width="55%" height="55%"/>
<br/>

### 개발 기간
| 기간 | 활동 |
|------|-----|
| 2022.9월 초 ~ 10월 말 | 와이어프레임 제작, 서비스 기획, DB & API 설계, 화면 구현 |
| 2022.11월 ~ 2023.2월 중순 | API 개발, 화면 구현 |
| 2023.2월 말 ~ 2023.3월 말 | API 연동, 리팩토링 및 마무리 |
<br/>

## 주요 기능 
### 공통
<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/5cb63530-d759-4cb2-9c20-3382805cc156" width="80%" height="80%"/>
<br/>
<br/>

<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/6b96d9a8-76a1-4c2f-a3d2-6675dd5f59a0" width="80%" height="80%"/>
<br/>
<br/>

<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/d3e995e5-2d94-4eb3-b46b-4a7ff63c5c15" width="80%" height="80%"/>
<br/>
<br/>

<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/17a20d6d-6abf-4f5c-8970-61c148acc88d" width="80%" height="80%"/>
<br/>
<br/>

### 사장
<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/b62aeb16-db5a-4636-9538-4117fe679d4b" width="80%" height="80%"/>
<br/>
<br/>

<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/f7b0f58e-268e-43ed-a323-84434535fcd7" width="80%" height="80%"/>
<br/>
<br/>

### 알바
<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/5e135165-afcc-4577-b8de-8d0828cc1ef4" width="80%" height="80%"/>
<br/>
<br/>

<img src="https://github.com/COALBA-Graduation-Project/.github/assets/65665065/18314727-c472-439b-aaf0-3cc4cbf80043" width="80%" height="80%"/>
<br/>
<br/>

## 팀원 및 역할
| 팀원 | 역할 |
|------|-----|
| 공통 | 와이어 프레임 제작, 서비스 기획 |
| 김다은 | 구글&네이버 로그인, 프로필&워크스페이스&스케줄 API, Beacon 구현, AWS 배포 |
| 신지연 | 대타근무요청&쪽지 API, FCM을 이용한 알림서버 구현, 외부캘린더와 스케줄 연동 기능 구현|
| 조예진 | 사장님, 알바생 앱의 모든 화면 및 기능 구현, 백엔드와의 서버 연동|
<br/>
