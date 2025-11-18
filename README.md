# 🧠 CodeSchooler – AI 기반 파이썬 학습 & 자동 채점 웹 서비스

CodeSchooler는 **AI 챗봇을 활용한 파이썬 이론 학습**, **자동 연습문제 생성**, **코드 채점 및 오류 분석**, **실시간 피드백**을 제공하는 웹 기반 학습 플랫폼입니다.

사용자는 자연어로 질문하고, 실습 환경에서 코드를 작성하여 즉시 채점/피드백을 받을 수 있습니다.

---

# ✨ 주요 기능

## 🔹 1. AI 학습 챗봇 (LearnPage)

* 자연어 질문 → AI가 즉시 개념 설명
* 주제 기반 "이론" or "연습문제" 생성
* ReactMarkdown 기반 렌더링
* CodeMirror로 코드 예제 하이라이팅
* 응답 속도 타이머 + 타이핑 애니메이션 지원
* 사용자 학습 상태 저장 (세션별 user_id 유지)

---

## 🔹 2. 코드 작성 & 자동 채점

파이썬 코드 편집기 기능 제공:

* ✓ CodeMirror 기반 파이썬 코드 에디터
* ✓ 문제 번호 선택 (001, 002, 003 …)
* ✓ Flask 서버로 코드 제출
* ✓ RestrictedPython 기반 샌드박스 실행
* ✓ 정답 코드와 출력 비교
* ✓ HuggingFace 오류 분류 모델을 통한 상세 오류 분석
* ✓ Gemini 모델을 통한 수정 제안 생성

백엔드는 AI 기반으로 다음을 수행합니다:

* 오답 여부와 차이 분석
* semantic output 비교
* 코드 실행 결과 기록
* 오류 라인, 오류 유형 분석
* JSON 기반 피드백 생성

---

## 🔹 3. 자동 연습문제 생성 & 정답 코드 자동 생성

* Gemini 모델로 문제 3개 자동 생성
* 입/출력 예시 포함
* 각 문제별 정답 코드 자동 생성
* 문제는 DB(8080 서버)에 저장 가능
* 사용자 progress에 저장되어 이어서 학습 가능

---

## 🔹 4. 메인 페이지(슬라이드 쇼)

* fade 애니메이션 적용된 이미지 캐러셀
* 자동 재생, 일시정지
* 전체 화면 모달 지원
  → 서비스 소개용 랜딩 페이지 구성

---

## 🔹 5. 로그인, 사이드바, 다크 모드 지원

* LoginModal로 로그인 처리
* Sidebar 컴포넌트로 페이지 이동
* 학습 모드에서는 자동 다크 모드 적용

---

# 🛠 기술 스택

### **Frontend**

* React
* React Router
* CodeMirror (Python + VSCode Dark Theme)
* React Markdown
* Prism Syntax Highlighter
* Fetch 기반 API 통신
* Custom CSS 애니메이션
* Fullscreen Loading Overlay

### **Backend**

* Flask
* Gemini 1.5 Flash (Google Generative AI)
* HuggingFace Transformers
* Custom Error Classification Model
* RestrictedPython (코드 샌드박스)
* aiohttp 비동기 HTTP 통신
* Difflib 기반 코드 유사도 분석

### **AI 모델**

* Gemini 1.5 Flash – 내용 생성, 문제 생성, 오류 분석
* HuggingFace 모델
  → `MilkTeaaaaaeee/1235657` (오류 분류)

---

# 📂 프로젝트 구조

```
frontend/
 ├─ App.js
 ├─ MainPage.jsx
 ├─ LearnPage.jsx
 ├─ Sidebar.jsx
 ├─ LoginModal.jsx
 └─ PerformanceVisualizations.jsx

backend/
 ├─ app.py                # Flask 서버 & API 엔드포인트
 ├─ ai.py                 # 모든 AI 로직
 │   ├─ ContentGenerator  # 이론/문제/정답 생성
 │   ├─ CodeVerifier      # 코드 실행/오류 분석
 │   ├─ UserSessionManager
 │   ├─ PythonTutor       # 전체 AI 파이프라인
 ├─ requirements.txt
```

---

# 🚀 실행 방법

## 1️⃣ 프론트엔드

```bash
cd frontend
npm install
npm start
```

기본 실행 주소:
👉 [http://localhost:3000](http://localhost:3000)

---

## 2️⃣ 백엔드 실행 (Flask)

### 필요한 환경변수

```bash
OPENAI_API_KEY=your_gemini_api_key
HF_TOKEN=your_huggingface_token
```

### 실행

```bash
cd backend
pip install -r requirements.txt
python app.py
```

기본 실행 주소:
👉 [http://127.0.0.1:5000](http://127.0.0.1:5000)

---

# 🔗 주요 API 정리

### 📌 1. 챗봇 요청

```
POST /api/chat
{
  "message": "문자열 자료형 이론",
  "user_id": "123"
}
```

### 📌 2. 코드 제출

```
POST /api/submit-code
{
  "user_id": "123",
  "problem_number": "001",
  "code": "print('hello')"
}
```

### 📌 3. 문제 변경

```
POST /api/change-problem
```

---

# 🧬 AI 처리 흐름도

```
사용자 질문/코드 입력
      ↓
Flask(app.py)
      ↓
PythonTutor(ai.py)
      ↓
[이론/문제/채점 분기]
      ↓
ContentGenerator / CodeVerifier / SessionManager
      ↓
Gemini 모델 + RestrictedPython + 오류 분류 AI
      ↓
결과 반환
      ↓
React 화면 표시
```

---

# 🎨 UI 구성

### ✔ 챗봇 모드

* 대화 기반 학습
* Markdown 렌더링
* 타이핑 효과

### ✔ 코드 작성 모드

* VSCode Dark Theme 에디터
* 문제 번호 전환
* "채점 중…" 풀스크린 로딩

### ✔ 메인페이지

* 이미지 슬라이더 + 모달

---

# 📌 향후 개선 예정

* 사용자별 누적 점수 / 통계 대시보드
* CodeRunner 확장 (자바, C, JS)
* 난이도 자동 조절 학습 모드
* 학습 히스토리 저장/로드

---

# ❤️ 오픈소스 & 크레딧

* Google Gemini API
* HuggingFace Transformers
* CodeMirror
* RestrictedPython
* React / Flask




