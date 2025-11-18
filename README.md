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

# 🏗 전체 아키텍처 (Architecture)

```
┌──────────────────────────────┐        ┌──────────────────────────┐
│         React Frontend       │        │     Spring Boot API      │
│  - LearnPage.jsx             │ <----> │  - User / Answer / Code  │
│  - Login / Code Editor       │        │  - MySQL 저장             │
└───────────────▲──────────────┘        └────────────▲─────────────┘
                │                                      │
                │ REST API                              │ REST API
                ▼                                      ▼
        ┌──────────────────────┐            ┌──────────────────────┐
        │   Flask AI Server    │            │       MySQL DB       │
        │ - Gemini 이론/문제 생성        │            │ users, answers,       │
        │ - HF 오류분석 모델            │            │ code_submissions      │
        │ - RestrictedPython 실행      │            └──────────────────────┘
        └──────────────────────┘
```

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

