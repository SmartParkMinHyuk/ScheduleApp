
# 🗓️ 일정 관리 API

Spring Boot + JDBC Template 기반의 일정 관리 API입니다.  
사용자는 작성자 정보와 할 일을 입력하여 일정을 생성하고, 조회/수정/삭제할 수 있습니다.

---


## ▶️ 실행 방법

현재 해당 프로젝트는 특별한 설정 없이 **로컬 환경에서 실행 가능**합니다.

### 1. 개발 도구
- IntelliJ 또는 Eclipse에서 프로젝트 열기
- JDK 17 이상 설치
- MySQL DB 실행 및 연결 정보 확인

### 2. application.properties 설정 예시

```properties
spring.application.name=ScheduleApp
spring.datasource.url=jdbc:mysql://localhost:3306/schedule
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

> `schedule` 데이터베이스는 직접 생성해야 하며, 필요한 테이블 DDL도 함께 적용해주세요.

### 3. 실행 방법

- `ScheduleAppApplication.java` 클래스에서 `main()` 메서드 실행

```java
@SpringBootApplication
public class ScheduleAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleAppApplication.class, args);
    }
}
```

실행 후 `localhost:8080`에서 API 요청 테스트 가능 (Postman 등으로)

---

## ✅ 주요 기능

| 기능 | 설명 |
|------|------|
| **일정 생성** | 이름, 이메일, 할 일, 비밀번호 입력 |
| **전체 일정 조회** | 모든 스케줄을 최신순으로 반환 |
| **작성자 ID로 조회** | 특정 작성자의 모든 일정 조회 |
| **일정 수정** | 작성자명, 할 일 수정 (비밀번호 일치 시) |
| **일정 삭제** | 비밀번호 일치 시 삭제 가능 |

---

## 🧱 주요 구조

### 📂 DTO 설계

- `ScheduleRequestDto`  
  내부에 `ScheduleCreateReq`, `ScheduleUpdateReq`, `ScheduleDeleteReq` 세 가지 요청 클래스 존재

```java
public static class ScheduleCreateReq {
    String name;
    String email;
    String description;
    String password;
}
```

- `ScheduleResponseDto`
  - 내부 `ScheduleRes` 클래스: 응답 객체 구조 통일

```java
@Builder
public ScheduleRes(Writer writer, Schedule schedule)
```

---

### 📂 Controller

```java
@PostMapping         // 스케줄 생성
@GetMapping          // 전체 조회
@GetMapping("/{id}") // 작성자 기준 조회
@PatchMapping("/{id}") // 일정 수정
@DeleteMapping("/{id}") // 일정 삭제
```

모든 요청은 DTO로 받고, 응답은 `ScheduleRes`로 통일하여 반환

---

## 🧠 고민 & 해결

### 1. ✅ DTO 구조의 정리
- 생성/수정/삭제를 하나의 DTO 파일 내에서 내부 클래스로 구분
- 유지보수성과 명확한 역할 분리가 가능해짐

### 2. ✅ Optional과 Writer 재사용
- 동일한 이메일과 이름의 작성자는 새로 만들지 않고 재사용
- `existCheckWriter(name, email)` 내부에서 Optional로 분기 처리

### 3. ✅ Entity & 응답 설계
- `Schedule`과 `Writer` 엔티티 분리
- 응답에서는 `ScheduleRes(Writer, Schedule)`로 통합 제공

---

## 🔐 예외 처리 정책

| 상황 | HTTP 상태 |
|------|------------|
| 필수값 누락 | `400 BAD REQUEST` |
| 비밀번호 불일치 | `401 UNAUTHORIZED` |
| 데이터 없음 | `404 NOT FOUND` |
| 정상 삭제 | `200 OK` |
| 생성 완료 | `201 CREATED` |

---

## 💡 학습 포인트

- DTO 분리와 클래스 내부 정리 방식
- `Optional`을 활용한 객체 재사용
- `LocalDateTime`과 `Date` 타입 병행 사용
- 유효성 검증 (`@Validated`, `@NotBlank`, `@Email`)
- REST API 설계 및 명확한 상태 코드 응답
- 컨트롤러에서의 명시적 응답 구조 (`ResponseEntity`)

---

## 🚀 향후 개선 사항

- 테스트 코드 작성 (서비스, 컨트롤러 단위)
- Swagger/OpenAPI 연동
- 인증 방식 (JWT 등) 도입
- 작성자 이메일 중복 체크 강화

---

## 🙌 회고

> “DTO를 잘 설계하고, 책임을 분리하는 것이 얼마나 중요한지 체감했다.  
> 복잡해 보이는 구조도 명확한 역할 구분과 일관성 있는 설계만 있으면  
> 유지보수 가능한 좋은 코드가 된다는 걸 배웠다.”


---


# 📘 일정 관리 API 명세서

Spring Boot + JDBC Template 기반 일정 관리 서비스의 RESTful API 명세입니다.

---

## 🔐 공통 정보

- Base URL: `http://localhost:8080`
- Content-Type: `application/json`
- 인코딩: UTF-8

---

## 📌 1. 스케줄 생성

- **URL**: `/schedules`
- **Method**: `POST`
- **Request Body**:

```json
{
  "name": "홍길동",
  "email": "hong@test.com",
  "description": "할 일 내용",
  "password": "1234"
}
```

- **Response**: `201 CREATED`

```json
{
  "id": 1,
  "writer_id": 1,
  "email": "hong@test.com",
  "name": "홍길동",
  "description": "할 일 내용",
  "createdAt": "2024-03-24T10:00:00",
  "updatedAt": "2024-03-24T10:00:00"
}
```

---

## 📌 2. 전체 스케줄 조회

- **URL**: `/schedules`
- **Method**: `GET`
- **Response**: `200 OK`

```json
[
  {
    "id": 1,
    "writer_id": 1,
    "email": "hong@test.com",
    "name": "홍길동",
    "description": "할 일 내용",
    "createdAt": "2024-03-24T10:00:00",
    "updatedAt": "2024-03-24T10:00:00"
  }
]
```

---

## 📌 3. 작성자 ID로 스케줄 조회

- **URL**: `/schedules/{writer_id}`
- **Method**: `GET`
- **Response**: `200 OK`
- 
```json
[
  {
    "id": 25,
    "writer_id": 7,
    "email": "2323@1323.com",
    "name": "홍길",
    "description": "운동하기",
    "createdAt": "2025-03-25",
    "updatedAt": "2025-03-25"
  },
  {
    "id": 24,
    "writer_id": 7,
    "email": "2323@1323.com",
    "name": "홍길",
    "description": "운동하기",
    "createdAt": "2025-03-25",
    "updatedAt": "2025-03-25"
  },
  {
    "id": 23,
    "writer_id": 7,
    "email": "2323@1323.com",
    "name": "홍길",
    "description": "운동하기",
    "createdAt": "2025-03-25",
    "updatedAt": "2025-03-25"
  },
  {
    "id": 21,
    "writer_id": 7,
    "email": "2323@1323.com",
    "name": "홍길",
    "description": "운동하기",
    "createdAt": "2025-03-25",
    "updatedAt": "2025-03-25"
  },
  {
    "id": 22,
    "writer_id": 7,
    "email": "2323@1323.com",
    "name": "홍길",
    "description": "운동하기",
    "createdAt": "2025-03-25",
    "updatedAt": "2025-03-25"
  },
  {
    "id": 20,
    "writer_id": 7,
    "email": "2323@1323.com",
    "name": "홍길",
    "description": "운동하기",
    "createdAt": "2025-03-25",
    "updatedAt": "2025-03-25"
  }
]
```
---

## 📌 4. 스케줄 수정

- **URL**: `/schedules/{id}`
- **Method**: `PATCH`
- **Request Body**:

```json
{
  "name": "홍길동",
  "description": "수정된 내용",
  "password": "1234"
}
```

- **Response**: `200 OK`

---

## 📌 5. 스케줄 삭제

- **URL**: `/schedules/{id}`
- **Method**: `DELETE`
- **Request Body**:

```json
{
  "password": "1234"
}
```

- **Response**: `200 OK`

---

## ⚠️ 에러 코드

| 상태 코드 | 설명 |
|-----------|------|
| `400` | 필수 파라미터 누락 |
| `401` | 비밀번호 불일치 |
| `404` | 데이터 없음 |
| `201` | 생성 성공 |
| `200` | 조회/수정/삭제 성공 |

---

## 🧪 테스트 도구

- Postman

---


## 🧩 ERD 구조

```
Writer
┌──────────────┐
│ id (PK)      │
│ name         │
│ email        │
│ created_at   │
│ updated_at   │
└──────────────┘

      ▲
      │
      │ writer_id (FK)
      │
Schedule
┌──────────────┐
│ id (PK)      │
│ writer_id    │
│ description  │
│ password     │
│ created_at   │
│ updated_at   │
└──────────────┘
```

- 하나의 `Writer`는 여러 개의 `Schedule`을 가질 수 있습니다 (1:N 관계)
- `writer_id`는 외래 키로 `Writer` 테이블의 `id`를 참조합니다.


