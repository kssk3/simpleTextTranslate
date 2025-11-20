package woowacourse.textTranslate.error;

public enum ErrorMessage {
    INVALID_TEXT_INPUT("입력값이 비어있습니다."),
    INVALID_NOT_KOREAN_TEXT_INPUT("잘못된 입력입니다. 한글 텍스트를 입력해주세요."),
    INVALID_TRANSLATION_FAILED("번역에 실패했습니다. 번역 결과가 한국어로 반환되었습니다."),
    INVALID_CHOICE_LANGUAGE("잘못된 선택입니다. 기본값(영어)로 설정합니다."),
    INVALID_CHOICE_MODE_SELECTOR("잘못된 선택입니다. 기본값(CLI)로 실행합니다."),
    INVALID_NAVER_CLIENT_API("Naver API 값이 없습니다. .env 파일에 NAVER_CLIENT_ID와 NAVER_CLIENT_SECRET을 설정해주세요."),

    NAVER_CLIENT_ID("Client ID Key 값이 설정되지 않았습니다."),
    NAVER_CLIENT_SECRET("Client Secret key 값이 설정되지 않았습니다."),

    RESPONSE_BODY_EMPTY("API 응답 본문이 비어있습니다.");


    private final String message;


    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
