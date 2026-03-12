# PHOTO LINER API
> [!CAUTION]
> ### 🚨 서비스 종료 안내 (Service Terminated)
> 본 서비스는 **2026년 01월 01일**부로 운영이 종료되었습니다.
> ~~[서비스 바로가기](https://dev.photo-liner.kro.kr/)~~ (접속 불가)

> **여행의 모든 순간을 지도 위에 그리다**  
> Photo Liner는 사진에 담긴 순간들을 **지도 위에서 직관적으로 되살려 주는 위치 기반 사진 서비스**입니다.  
> 이 레포지토리는 Photo Liner 서비스의 **백엔드 API 서버**입니다.

---

## 🗺️ 서비스 소개

### 어떤 경험을 제공하나요?

Photo Liner는 단순한 사진 보관함이 아니라, **여행 타임라인을 지도 위에 그려주는 서비스**입니다.

- 스마트폰/카메라에서 찍은 사진을 업로드하면
  - 메타데이터에서 **촬영 위치와 날짜**를 읽어오고,
  - 누락된 정보는 사용자가 **지도에서 직접 위치를 선택**하여 보정합니다.
- 사진은 **촬영 날짜별로 자동 그룹화**되며,
- 지도 위에는
  - 개별 사진 마커,
  - 다수의 사진을 모아 표시하는 **클러스터 마커**가 함께 보여집니다.
- 선택한 앨범에 대해서는
  - **촬영 순서대로 경로를 그리고**,  
  - 슬라이더를 활용해 **하루 여행 동선을 따라가듯 재생**할 수 있습니다.

---

## ✨ 주요 기능

### 1. 사진 업로드 및 메타데이터 보정
<img width="1899" height="925" alt="image" src="https://github.com/user-attachments/assets/25facb91-f266-4383-b67a-b9ef84867add" />

- 다중 사진 선택 후 한 번에 업로드
- **사진 선택 모달**에서 업로드 대상 선택/해제
- 업로드 이후, 사진별로
  - 촬영 위치가 없으면 지도에서 클릭하여 위치 지정
  - 촬영 날짜가 없거나 잘못된 경우 직접 수정
- 모든 사진의 정보가 준비되면 한 번에 저장

### 2. 지도 기반 사진 보관함
<img width="1911" height="937" alt="image" src="https://github.com/user-attachments/assets/49a68580-5c21-4060-9a50-ee5298caeeba" />

- 좌측 패널: **날짜별로 그룹화된 썸네일 리스트**
- 우측 지도: 네이버 지도 위에 사진 썸네일 마커 표시
- 줌 레벨에 따라 자동으로 **마커 클러스터링**
  - “3개”, “2개”와 같이 사진 개수 표시
  - 클러스터 클릭 시 해당 영역으로 줌인

### 3. 앨범 & 경로 시각화
<img width="1909" height="939" alt="image" src="https://github.com/user-attachments/assets/62c20ab4-43ed-42a3-8812-40408092fa06" />

- 원하는 사진들을 묶어서 **앨범** 생성
- 앨범 뷰에서는
  - 지도 위에 앨범 내 사진들만 표시
  - **촬영 시각 기준으로 정렬 된 경로 라인**을 점선으로 표시
  - 시작/끝 지점, 이동 순서 등 동선이 한눈에 보이도록 UI 제공
- 하단 컨트롤에서
  - `1 / 5` 형식의 인덱스와 날짜를 보여주며,
  - 좌우 화살표로 **사진 순회 & 동선 따라가기** 가능

### 4. 사진 상세 보기
<img width="1910" height="935" alt="image" src="https://github.com/user-attachments/assets/7f747017-cbd0-4bee-af0b-c218e87a7518" />

- 지도 위의 사진 마커 또는 썸네일을 클릭 시
  - 큰 이미지와 함께 촬영 날짜, 위치 정보 등 메타데이터가 표시되는 모달 팝업
- 위치 정보가 있는 경우 “위치 정보 있음” 형태로 표기

### 5. 개별 사진 날짜 및 위치 수정
<img width="1912" height="939" alt="image" src="https://github.com/user-attachments/assets/598a2037-81a0-44a0-9daa-774bb6bde0b9" />
<img width="1911" height="935" alt="image" src="https://github.com/user-attachments/assets/f0c27378-7242-44e5-9580-43b7cd8a25e5" />

- 개별 이미지의 날짜 및 위치 수정
  
### 6. 카카오 로그인
<img width="1889" height="926" alt="image" src="https://github.com/user-attachments/assets/3a55496e-86a6-4235-b08a-02a1171536f3" />

- 카카오톡 계정 기반 로그인
- 로그인 후 개인별 사진 보관함 및 앨범 관리

---

## 🧱 시스템 아키텍처

아래는 Photo Liner의 인프라 구조입니다.

<img width="1041" height="678" alt="image" src="https://github.com/user-attachments/assets/d76ca9b1-321f-43b9-a640-c094e2cdaeb2" />


### 구성 요소

- **Users**
  - 브라우저에서 React 앱 접속
  - 지도/이미지/앨범 관련 API 호출

- **CloudFront**
  - 정적 리소스(React 번들) 및 이미지 캐싱
  - S3 및 EC2의 콘텐츠를 전 세계에 빠르게 전달

- **S3**
  - 원본/리사이즈된 사진 파일 저장소
  - EC2 백엔드로부터 **Pre-signed URL**을 발급 받아 클라이언트에서 직접 업로드

- **Lambda**
  - S3 업로드 이벤트 기반 트리거
  - 원본 이미지 리사이징, 썸네일 생성 등 비동기 이미지 처리

- **EC2**
  - Spring Boot 기반 **PHOTO_LINER_API** 서버
  - React 앱 서빙 및 REST API 제공
  - RDS, SSM, S3 등과 통신

- **RDS**
  - 사진/위치/앨범/사용자 정보를 저장하는 관계형 DB
  - 위치 정보는 `POINT` 타입으로 저장하여 공간 쿼리 지원

- **Systems Manager (Parameter Store)**
  - DB 비밀번호, 외부 API 키 등 **민감 정보** 관리

- **GitHub**
  - 코드 저장소
  - CI/CD 파이프라인 트리거 → EC2 배포

- **KakaoTalk (OAuth)**
  - 카카오 로그인을 통한 사용자 인증 연동

---

## 🛠 기술 스택

### Backend

- **Java 21**
- **Spring Boot 3.5.7**
  - Spring Web (REST API)
  - Spring Data JPA
  - Spring Validation
- **Hibernate Spatial**
  - 위도/경도 `Point` 타입 매핑
  - 지도 뷰포트 내 사진 조회 등 공간 쿼리 처리
- **Flyway**
  - DB 마이그레이션 관리

### Infra & Storage

- **AWS EC2** – 애플리케이션 서버
- **AWS RDS (MySQL 8.1)** – 메인 데이터베이스
- **AWS S3** – 원본/썸네일 이미지 저장소
- **AWS Lambda** – 이미지 리사이징 자동화
- **AWS CloudFront** – 정적 파일 및 이미지 CDN
- **AWS Systems Manager (Parameter Store)** – 설정/보안 값 관리

---

## 📁 패키지 구조

### PHOTO_LINER_API 프로젝트 구조

```
PHOTO_LINER_API
├── .github/
├── gradle/
├── src
│   ├── main
│   │   ├── java/kr/kro/photoliner
│   │   │   ├── common/model        # 공통 모델 (BaseEntity)
│   │   │   ├── domain
│   │   │   │   ├── album           # 앨범 도메인
│   │   │   │   ├── photo           # 사진 도메인
│   │   │   │   └── user            # 사용자 도메인
│   │   │   └── global
│   │   │       ├── auth            # JWT 인증
│   │   │       ├── code            # API 응답 코드
│   │   │       ├── config          # 설정 (S3, Swagger, WebMvc 등)
│   │   │       ├── exception       # 전역 예외 처리
│   │   │       └── kakao/login     # 카카오 로그인 연동
│   │   └── resources
│   │       ├── application-*.yml   # 프로필 별 설정
│   │       └── db/migration        # Flyway 스크립트 (V1~V9)
│   └── test
├── build.gradle
├── settings.gradle
└── gradlew / gradlew.bat
```

### 도메인 구조

각 도메인(album, photo, user)은 동일한 레이어 구조를 따릅니다:

```
domain/{name}
├── controller    # API 엔드포인트
├── dto           # 요청/응답 DTO
├── model         # 엔티티
├── repository    # JPA Repository
├── service       # 비즈니스 로직
└── infra         # 외부 연동
```

---
## 🧑‍🧑‍🧒‍🧒 Team Members

### BackEnd
| <img src="https://github.com/user-attachments/assets/b217bbd3-9a6e-4262-abd3-46acb1fd3cbd" width="130"> | <img width="130" alt="image" src="https://github.com/user-attachments/assets/fd726b08-1423-45f3-82fc-1177e25e5f5b" /> |
| :--: | :--: |
| [강인화](https://github.com/kih1015) | [박희찬](https://github.com/chanrhan) |

### FrontEnd
| <img width="130" alt="image" src="https://github.com/user-attachments/assets/99c7b208-2eb6-43ac-9956-64ec9b338031" /> | <img width="130" alt="image" src="https://github.com/user-attachments/assets/2bcce960-0d43-4c8b-9e9b-cd8815079f17" /> | <img width="130" alt="image" src="https://github.com/user-attachments/assets/d4592cbc-c4f1-4c31-9f18-b56e1849f056" /> |
| :--: | :--: | :--: |
| [Claud] | [Cursor] | [AntiGravity] |
