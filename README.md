# 프리코스 자유 미션 (간단한 텍스트 번역 프로그램)

**한국어를 영어로 번역해주는 애플리케이션**

## 프로젝트 구성

- **현재 프로젝트는 Swing GUI 데스크톱 애플리케이션 개발 단계**
- **IntelliJ IDEA 플로그인 개발 예정**

---

## 🎯개발 목적

- 한글로 작성된 텍스트를 빠르게 영어로 번역
- 데스크톱 환경에서 독립적으로 사용 가능한 `Swing GUI` 개발
- IntelliJ IDEA에서 코드 작성 중 바로 번역할 수 있는 플로그인 (개발 예정)
    - AI를 활용해서 메서드단에서 Ai가 코드 동작을 보고 메서드 네이밍을 제공하는 플러그인으로 개발 예정

---

## 🎨아키텍쳐 설계

**계층 분리 (Layered Architecture)**

- 유지보수성 : 각 계층의 책임이 명확하고 수정이 용이함
- 확장성 : 새로운 번역 API 추가 시 Service 계층만 확장하면 됨
- 테스트 용이성 : 각 계층을 독립적으로 테스트 가능

**View(TranslatorGUI) → Controller(TranslateController) → Domain(Translator, KoreanText) → Service(PapagoService) → API
**

**Value Object**

- koreanText : 한글 입력 검증을 캡슐화
- 정규식 검증 : `^[ㄱ-ㅎㅏ-ㅣ가-힁0-9\\s\\p{Punct}]*$`
- 한글, 숫자, 특수문자, 공백만 허용

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

구현:

- OkHttpClient로 HTTP 요청
- Gson으로 Json 파싱
- 환경변수(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET)에서 인증 정보 로드

구현 이유:

- Spring 환경 개발이 아닌 간단한 GUI 기반이라 OkHttp3 사용
- Spring 환경에 잘 부합하는 application.yml 대신 env 값을 이용해서 개발
- API 값을 직접 하드 코딩하면 안전의 이유로 Dotenv 활용해 값을 활용함

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


출처 :
- https://api.ncloud-docs.com/docs/ai-naver-papagotranslation-example01  
- https://api.ncloud-docs.com/docs/ai-naver-papagonmt-translation
- https://hbase.tistory.com/90
- https://square.github.io/okhttp/


  

