# 프리코스 자유 미션 (간단한 텍스트 번역 프로그램)

**한국어를 영어로 번역해주는 애플리케이션**

## 📋 프로젝트 개요

이 프로젝트는 **IntelliJ IDEA 플러그인 개발을 위한 사전 단계**로 시작되었습니다.

### 최종 목표

AI를 활용하여 메서드의 구현 로직과 반환값을 분석하고, 적절한 메서드명을 자동으로 제안하는 IntelliJ IDEA
플러그인 개발

### 현재 단계

1. **CLI/GUI 기반 번역 애플리케이션 구현** (현재 단계)
    - 독립 실행 가능한 Swing GUI 데스크톱 애플리케이션
    - 명령줄 인터페이스(CLI) 모드 지원
    - 순수 Java 기반의 계층형 아키텍처 설계

2. **IntelliJ IDEA 플러그인 개발** (예정)
    - 코드 작성 중 실시간 번역 기능
    - AI 기반 메서드명 자동 생성 기능

---

## 🎯개발 목적

- 한글로 작성된 텍스트를 빠르게 영어로 번역
- 데스크톱 환경에서 독립적으로 사용 가능한 `Swing GUI` 개발
- IntelliJ IDEA에서 코드 작성 중 바로 번역할 수 있는 플로그인 (개발 예정)
    - AI를 활용해서 메서드단에서 Ai가 코드 동작을 보고 메서드 네이밍을 제공하는 플러그인으로 개발 예정

---

## 🛠️ 기술 스택 및 선택 이유

### 순수 Java 기반 구현

**Spring Framework를 사용하지 않은 이유:**

1. **학습 목적**: 객체지향 설계와 계층형 아키텍처를 순수 Java로 직접 구현하여 이해도 향상
2. **경량성**: 간단한 GUI 애플리케이션에 Spring의 무거운 DI 컨테이너가 불필요
3. **플러그인 개발 준비**: IntelliJ 플러그인은 Spring 없이 동작하므로, 순수 Java 기반 설계 경험이 필요
4. **빠른 실행**: Spring Boot의 초기화 시간 없이 즉시 실행 가능

### 주요 라이브러리

- **OkHttp3**: HTTP 통신 (Spring의 RestTemplate 대신)
- **Gson**: JSON 파싱 (Spring의 Jackson 대신)
- **Dotenv-java**: 환경변수 관리 (Spring의 application.yml 대신)
- **MockWebServer**: 외부 API 테스트를 위한 Mock 서버
- **JUnit5 + AssertJ**: 테스트 프레임워크

---

## 🏗️ 프로젝트 구조

```
src/main/java/woowacourse/textTranslate/ 
├── Application.java              # 애플리케이션 진입점. 
├── config/                       # 설정 및 팩토리.
│   ├── ApiKeyProvider.java       # API 키 환경변수 로드
│   ├── HttpClientProvider.java   # OkHttpClient 싱글톤 관리
│   └── TranslatorFactory.java    # Translator 객체 생성
├── domain/                       # 도메인 모델 (비즈니스 로직)
│   ├── KoreanText.java           # 한글 텍스트 Value Object
│   ├── KoreanRegex.java          # 한글 검증 정규식
│   ├── TargetText.java           # 번역된 텍스트 Value Object
│   ├── TargetLanguage.java       # 번역 대상 언어 Enum
│   └── Translator.java           # 번역 도메인 로직
├── service/                      # 외부 API 통신
│   ├── TranslationService.java   # 번역 서비스 인터페이스
│   ├── PapagoTranslationService.java
│   └── KakaoTranslationService.java
├── view/                         # 사용자 인터페이스
│   ├── ApplicationMode.java      # CLI/GUI 모드 Enum
│   ├── ModeSelector.java         # 실행 모드 선택
│   ├── TranslatorView.java       # View 인터페이스
│   ├── cli/
│   │   ├── view/TranslatorCLI.java
│   │   └── controller/CliTranslateController.java
│   └── swing/
│       ├── view/TranslatorGUI.java
│       └── controller/GuiTranslateController.java
├── runner/                       # 실행 로직
│   └── ApplicationRunner.java
└── error/                        # 에러 메시지 정의
└── ErrorMessage.java
```

### 구조 설계 이유

**계층별 책임 분리:**

- `domain`: 비즈니스 로직과 검증 규칙 (프레임워크 독립적)
- `service`: 외부 API 통신 (교체 가능한 구현체)
- `view`: 사용자 인터페이스 (CLI/GUI 분리)
- `config`: 의존성 주입 및 객체 생성 책임

**Value Object 패턴:**

- `KoreanText`: 한글 입력 검증을 캡슐화
- `TargetText`: 번역 결과 불변성 보장
- 정규식 검증: `^[ㄱ-ㅎㅏ-ㅣ가-힁0-9\\s\\p{Punct}]*$` (한글, 숫자, 특수문자, 공백만 허용)

**Strategy 패턴:**

- `TranslationService` 인터페이스로 Papago/Kakao API 교체 가능

---

## 🎨아키텍쳐 설계

**계층 분리 (Layered Architecture)**

- 유지보수성 : 각 계층의 책임이 명확하고 수정이 용이함
- 확장성 : 새로운 번역 API 추가 시 Service 계층만 확장하면 됨
- 테스트 용이성 : 각 계층을 독립적으로 테스트 가능

**실행 흐름:**

```
View (CLI/GUI)
    ↓
Controller (CliTranslateController / GuiTranslateController)
    ↓
Domain (Translator, KoreanText)
    ↓
Service (PapagoService / KakaoService)
    ↓
External API (Papago / Kakao)
```

---   

## 테스트 전략

### MockWebServer를 활용한 외부 API 테스트

**테스트하기 어려운 부분:**

- 실제 Papago API 호출은 네트워크 상태, API 키 유효성, 요금 등의 문제로 테스트가 불안정

**해결 방법:**

- **MockWebServer** 사용으로 가짜 HTTP 서버를 로컬에서 실행
- 실제 API 응답을 시뮬레이션하여 안정적인 단위 테스트 구현

**테스트 예시** (`PapagoTranslationServiceTest.java`):

```java

@BeforeEach
void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();

    service = new PapagoTranslationService(
            "test-id",
            "test-secret",
            mockWebServer.url("/").toString(),
            new OkHttpClient()
    );
}

@Test
void 정상_응답_파싱_통과() {
    // Mock 응답 설정
    mockWebServer.enqueue(new MockResponse()
            .setBody("{\"message\":{\"result\":{\"translatedText\":\"Hello\"}}}")
            .setHeader("Content-Type", "application/json")
    );

    // 실제 호출처럼 동작하지만 MockWebServer에 요청
    TargetText result = service.translate("안녕하세요", "en");

    assertEquals("Hello", result.getTranslatedText());
}
```

테스트 커버리지:

- ✅ 정상 응답 파싱
- ✅ 다양한 언어 번역 (영어, 일본어)
- ✅ API 호출 실패 (500 에러)
- ✅ 잘못된 JSON 형식 처리
- ✅ null 값 검증
- ✅ 도메인 검증 로직 (한글 정규식 등)

---

## 🔌외부 API 통합

1. Naver Papago Translation API

선택한 이유:

- 높은 한굴 - 영어 번역 정확도
- 안정적인 서비스
- 무료 사용량 제공

사용 방법:

- POST https://papago.apigw.ntruss.com/nmt/v1/translation
- Headers:
    - X-NCP-APIGW-API-KEY-ID: {CLIENT_ID}
    - X-NCP-APIGW-API-KEY: {CLIENT_SECRET}
- 응답 예시: **Json**

```Json
{
  "message": {
    "result": {
      "srcLangType": "ko",
      "tarLangType": "en",
      "translatedText": "Hello, I like to eat apple while riding a bicycle."
    }
  }
}
```  

구현 방식:

- OkHttpClient로 HTTP 요청
- Gson으로 Json 파싱
- 환경변수(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET)에서 인증 정보 로드

구현 이유:

- Spring 환경 개발이 아닌 간단한 GUI 기반이라 OkHttp3 사용
- Spring 환경에 잘 부합하는 application.yml 대신 env 값을 이용해서 개발
- API 값을 직접 하드 코딩하면 안전의 이유로 Dotenv 활용해 값을 활용함

---

## ⚙️동작 방식

### Swing GUI 애플리케이션 실행 흐름

1. 사용자 입력  
   사용자가 텍스트 필드에 한글 입력 -> "번역하기" 버튼 클릭
2. 입력 검증  
   KoreanText.validate() -> KoreanRegex로 한글 여부 확인
    - 한글, 숫자, 특수문자, 공백만 허용
    - 검증 실패 시 에러 다이얼로그 표시
3. 번역 요청
   Controller → Translate -> TranslationService (Papago/kakao) - 현재 Papago 구현
4. API 호출  
   OkHttpClient로 HTTP POST 요청 -> JSON 응답 수신
5. 결과 표시  
   Gson으로 JSON 파싱 -> GUI 결과 영역에 번역문 표시

--- 

참고 자료 :

- https://api.ncloud-docs.com/docs/ai-naver-papagotranslation-example01
- https://api.ncloud-docs.com/docs/ai-naver-papagonmt-translation
- https://hbase.tistory.com/90
- https://square.github.io/okhttp/
- https://berrrrr.github.io/programming/2021/01/24/how-to-use-mockwebserver/
- https://github.com/square/okhttp/blob/master/mockwebserver/README.md
  
---
📝 개발 과정에서의 고민

왜 Spring을 사용하지 않았나?

- 학습: 순수 Java로 DI, 계층 분리를 직접 구현
- 경량: GUI 앱에 Spring Boot는 오버스펙
- 플러그인 준비: IntelliJ 플러그인은 Spring 없이 동작

왜 MockWebServer를 사용했나?

- 실제 API 호출은 네트워크 의존성이 크고 비용 발생
- 테스트 환경에서 일관된 응답 보장
- API 장애 상황도 시뮬레이션 가능

왜 Value Object를 사용했나?

- 도메인 규칙(한글 검증)을 캡슐화
- 불변성 보장으로 예측 가능한 코드
- 원시값 포장으로 타입 안정성 확보
  

