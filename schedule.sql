
# 📄 사용된 SQL 쿼리 모음

Spring Boot + JDBC Template 기반으로 작성된 일정 관리 API에서 사용된 주요 SQL 쿼리들을 정리한 문서입니다.

---

## 🏗️ 테이블 생성

```sql
CREATE TABLE writer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer_id BIGINT NOT NULL,
    description VARCHAR(200) NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (writer_id) REFERENCES writer(id) ON DELETE CASCADE
);
```

---

## 🔄 Insert

```sql
-- writer 저장 (SimpleJdbcInsert 사용)
INSERT INTO writer (name, email, created_at, updated_at) VALUES (?, ?, ?, ?);

-- schedule 저장 (SimpleJdbcInsert 사용)
INSERT INTO schedule (writer_id, description, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?);
```

---

## 📥 Select

```sql
-- 이름과 이메일로 작성자 조회
SELECT * FROM writer WHERE name = ? AND email = ?;

-- 작성자 ID로 조회
SELECT * FROM writer WHERE id = ?;

-- 전체 스케줄 조회 (작성자와 조인)
SELECT 
    s.id,
    s.writer_id,
    w.email,
    w.name,
    s.description,
    s.created_at,
    s.updated_at
FROM schedule s
JOIN writer w ON s.writer_id = w.id
ORDER BY s.created_at DESC;

-- 특정 작성자의 스케줄 조회
SELECT 
    s.id,
    s.writer_id,
    w.email,
    w.name,
    s.description,
    s.created_at,
    s.updated_at
FROM schedule s
JOIN writer w ON s.writer_id = w.id
WHERE s.writer_id = ?
ORDER BY s.created_at DESC;

-- ID로 스케줄 단건 조회
SELECT * FROM schedule WHERE id = ?;
```

---

## ✏️ Update

```sql
-- 작성자 이름 수정
UPDATE writer SET name = ?, updated_at = NOW() WHERE id = ?;

-- 일정 내용 수정
UPDATE schedule SET description = ?, updated_at = NOW() WHERE id = ?;
```

---

## ❌ Delete

```sql
-- 스케줄 삭제
DELETE FROM schedule WHERE id = ?;
```
