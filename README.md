# 🧩 CodeSchooler — AI 기반 파이썬 학습 플랫폼

**AI가 이론·문제·정답·피드백·코드 채점까지 모두 처리하는 지능형 학습 서비스**

> 📆 개발 기간: **2024.12.16 ~ 2025.05.13**
> 🧑‍💻 담당 역할: **백엔드(Spring Boot) + AI 서버(Flask/Python) + 프론트엔드 React 전체 기능 개발**

---

<div align="center">

### 🚀 “AI가 문제를 만들고, 채점하고, 피드백을 제공하는 신개념 파이썬 학습 플랫폼”

Python 이론 학습 → 문제 생성 → 코드 작성 → AI 채점 → 피드백 → 통계 시각화까지
**하나의 서비스에서 모두 경험할 수 있는 풀스택 프로젝트입니다.**

</div>

---

# ✨ 주요 기능 (Highlights)

### 🧠 1. **AI 기반 파이썬 이론 설명**

* Gemini 1.5 Flash 기반
* 주제 입력 시 **구체적 예시·응용 사례·코드 샘플** 포함하여 자동 생성

### 📝 2. **AI 자동 연습 문제 생성**

* 주제 기반 문제 3개 자동 생성
* *문제 설명 / 지시사항 / 입력 예 / 출력 예 / 난이도* 포함
* 문제마다 **정답 코드 자동 생성 → Spring Boot DB 저장**

### 🧪 3. **AI 코드 채점 엔진**

* RestrictedPython 환경에서 사용자 코드 샌드박스 실행
* HuggingFace 오류 분류 모델(MilkTeaaaaaeee/1235657)
* Gemini 기반 **오류 원인 분석 + 수정 제안 제공**
* 정답 코드와의 출력 비교
* Spring Boot로 제출 결과 저장

### 💬 4. **실시간 AI 챗봇**

* 이론/문제/힌트/추가 문제 요청 가능
* 자연어 기반 의도 분류(TFIDF + 나이브베이즈)
* 타이핑 애니메이션 + 응답시간 표시

### 👨‍💻 5. **코드 에디터 (CodeMirror + VSCode Dark Theme)**

* 파이썬 문법 하이라이트
* 자동 줄번호 / 자동 괄호 맞춤 / 다크모드
* 제출 시 풀스크린 로딩 애니메이션

### 📊 6. **학습 통계 시각화**

* 정답률 파이차트
* 일별 문제풀이 그래프
* 네오모픽 디자인의 대시보드 UI

### 🔐 7. **사용자 회원가입/로그인**

* Spring Security + BCrypt
* 로그인 유지 기능
* Spring Boot API로 관리

---

## 시스템 아키텍처

CodeSchooler는 다음 네 가지 컴포넌트로 구성된 웹 기반 파이썬 학습 플랫폼입니다.

![CodeSchooler 시스템 아키텍처](./docs/codeschooler-architecture.png)

---

### 1. Web Client (React SPA)

- React 기반 단일 페이지 애플리케이션(SPA)
- 주요 화면
  - 이론 학습 화면
  - 문제 풀이 및 코드 에디터(CodeMirror)
  - 채점/피드백 결과 보기
  - 학습 통계 대시보드(Recharts)
- 역할
  - 사용자 입력 처리(코드, 답안, 로그인 정보 등)
  - Axios를 통해 Spring Boot 백엔드 API 호출
  - JWT를 저장/전달하여 인증이 필요한 요청 전송
  - 실행 결과, 피드백, 통계를 시각적으로 렌더링

---

### 2. Backend API Server (Spring Boot)

- Spring Boot 기반 REST API 서버
- 주요 모듈
  - 인증/사용자 관리
    - 회원가입, 로그인, JWT 발급 및 검증
  - 학습 콘텐츠 관리
    - 이론(Theory), 문제(Problem), 예시 정답 관리
  - 제출/채점 기록 관리
    - 코드 제출(CodeSubmission), 답안(Answer) 저장
    - 실행 결과, 정답 여부, 피드백 저장
  - 통계/대시보드
    - 사용자별 정답률, 제출 횟수, 최근 학습 문제 조회
  - 외부 서비스 연동
    - Flask 코드 실행/피드백 서버와 HTTP 통신
- 구조
  - Controller → Service → Repository → MySQL
  - 주요 엔티티: User, Problem, Answer, CodeSubmission, Feedback, LearningStats

---

### 3. Code Execution & Feedback Server (Flask)

- Python/Flask 기반 보조 서버(마이크로서비스)
- 역할
  - 코드 실행
    - 제한된 샌드박스 환경에서 파이썬 코드 실행
    - 표준 출력, 리턴값, 예외 정보를 수집
  - 오류/코드 분석
    - 에러 메시지, 코드 패턴 등을 분석해 오류 유형 분류
  - 피드백 생성
    - 오류 유형에 따라 이해하기 쉬운 설명과 수정 방향 제안
  - Spring Boot와의 통신
    - 입력: 사용자 ID, 문제 ID, 코드 등
    - 출력: 실행 결과, 성공 여부, 오류 유형, 피드백(JSON)
- Spring Boot는 Flask 응답을 받아 DB에 저장하고, 다시 React로 전달합니다.

---

### 4. Database (MySQL)

- MySQL 기반 관계형 데이터베이스
- 주요 테이블(엔티티)
  - `users`
    - id, username, password, role, created_at, updated_at
  - `problems`
    - id, title, description, difficulty, topic, sample_input, sample_output
  - `theories`
    - id, title, content, topic, order_index
  - `answers`
    - id, user_id, problem_id, user_answer, is_correct, submitted_at
  - `code_submissions`
    - id, user_id, problem_id, code, result, is_success, error_type, created_at
  - `feedbacks` (또는 code_submissions에 포함)
    - id, submission_id, feedback_text
  - `learning_stats`
    - user별 제출 수, 정답 수, 최근 학습 일자, 주제별 통계 등 집계 데이터
- JPA를 통해 엔티티 간 연관 관계를 맺어 관리합니다.
  - User (1) : (N) CodeSubmission
  - Problem (1) : (N) CodeSubmission
  - CodeSubmission (1) : (1) Feedback (또는 1:N)

---

### 5. 요청 흐름 요약

1. 사용자가 React 클라이언트에서 로그인 → Spring Boot에서 JWT 발급
2. 문제/이론 조회 요청 → Spring Boot → MySQL에서 데이터 조회 → React에 응답
3. 코드 제출
   - React: `/api/submissions`로 코드 + JWT 전송
   - Spring Boot:
     - 사용자/문제 정보 검증 후 CodeSubmission 생성
     - Flask `/execute-code` API 호출
   - Flask:
     - 코드 실행 및 오류/피드백 생성 후 JSON 응답
   - Spring Boot:
     - 실행 결과와 피드백을 DB에 저장
     - 최종 결과를 React로 반환
   - React:
     - 코드 실행 결과, 피드백, 정답 여부를 화면에 표시
4. 학습 통계
   - React → Spring Boot `/api/stats/my`
   - Spring Boot → MySQL에서 제출/정답 데이터 집계
   - React에서 Recharts로 차트 렌더링

---

# 🧩 기술 스택

### 🎨 Frontend

* React
* CodeMirror
* React Router
* Recharts
* Axios
* VSCode Dark Theme
* Lucide Icons

### 🧬 Backend (Spring Boot)

* Spring Web / Spring Security / Spring Data JPA
* BCryptPasswordEncoder
* MySQL 8
* JJWT (토큰 기능 제거하여 NO_TOKEN 처리)
* Lombok

### 🤖 AI Server (Flask + Python)

* Google Gemini API
* HuggingFace Transformers
* RestrictedPython
* aiohttp + asyncio
* scikit-learn
* TfidfVectorizer + MultinomialNB
* Python subprocess / compile_restricted

---

# 📚 주요 기능 상세

## 1) AI 이론 생성

```python
response = await self.generate_content_async(prompt)
theory = response.text
```

## 2) AI 문제 생성 + 정답 자동 생성 + DB 저장

```python
exercise["correct_answer"] = await self.generate_answer(...)
await api_client.save_answer(data)  # Spring Boot로 저장
```

## 3) 코드 채점 (RestrictedPython)

```python
compiled_code = compile_restricted(code, "<string>", "exec")
exec(compiled_code, restricted_globals, locals_dict)
```

## 4) 오류 유형 분류 (HuggingFace 모델)

```python
outputs = self.error_model(**inputs)
predicted = torch.argmax(outputs.logits, dim=1)
```

## 5) 오류 분석 및 수정 제안 (Gemini)

```python
response = await self.gemini_model.generate_content(prompt)
return response.text
```

## 6) AI 챗봇 의도 분류

```python
self.intent_classifier = make_pipeline(TfidfVectorizer(), MultinomialNB())
```

---

# 🧩 DB 모델

### ✔ users

| Column     | Type      | Desc    |
| ---------- | --------- | ------- |
| id         | bigint PK | 사용자 ID  |
| name       | varchar   | 이름      |
| email      | varchar   | 로그인 이메일 |
| password   | varchar   | 암호화된 PW |
| rememberMe | boolean   | 로그인 유지  |
| createdAt  | datetime  | 자동 생성   |

### ✔ answers

| Column        | Type      | Desc  |
| ------------- | --------- | ----- |
| id            | bigint PK | 정답 ID |
| problemNumber | varchar   | 문제 번호 |
| answerCode    | text      | 정답 코드 |
| createdAt     | datetime  | 생성 시간 |

### ✔ code_submissions

| Column        | Type      | Desc   |
| ------------- | --------- | ------ |
| id            | bigint PK | 제출 ID  |
| userId        | bigint    | 사용자 ID |
| problemNumber | varchar   | 문제 번호  |
| code          | text      | 제출 코드  |
| isCorrect     | boolean   | 정답 여부  |
| feedback      | text      | AI 피드백 |
| submittedAt   | datetime  | 제출 시간  |

---

# 🔌 API 명세서

## 📘 Spring Boot API

### ✔ 회원가입

`POST /api/users/signup`

### ✔ 로그인

`POST /api/users/login`

### ✔ 정답 저장

`POST /api/save-answer`

### ✔ 정답 조회

`GET /api/get-answer/{problemNumber}`

### ✔ 제출 코드 저장

`POST /api/submit-code`

---

## 🤖 Flask AI API

### ✔ 대화

`POST /api/chat`

### ✔ 코드 제출(채점 요청)

`POST /api/submit-code`

---

# 🖥 실행 방법

## 1) Spring Boot (백엔드)

```bash
cd backend
./gradlew bootRun
```

## 2) AI 서버 (Flask)

```bash
cd ai-server
pip install -r requirements.txt
python app.py
```

## 3) React 클라이언트

```bash
cd client
npm install
npm start
```

---

# 📌 담당 역할 (본인 기여도)

### 🟩 **백엔드 — Spring Boot 전 기능 개발**

* User / Answer / CodeSubmission 전 설계 및 구현
* Spring Security 적용
* MySQL 테이블 설계
* REST API 설계 및 개발

### 🟨 **프론트엔드 — React UI 개발**

* AI Chat UI, CodeMirror Editor, 대시보드 개발
* 타입라이팅 효과 / ChatLoader / ResponseTimer
* 로그인/회원가입 UX
* 전체 다크모드 & 슬라이더 UI

---

# 📈 향후 개선 방향

* 중급/고급 난이도 문제 자동 생성
* 추가 통계(사용자별 난이도 매트릭스)
* 웹소켓 기반 실시간 스트리밍 타이핑
* 문제 풀기 랭킹 시스템

---

