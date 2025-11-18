package woowacourse.textTranslate.swing.error;

public enum ErrorMessage {
    INVALID_TEXT_INPUT("번역할 한글을 입력해주세요"),
    INVALID_CHOICE_LANGUAGE("잘못된 선택입니다. 기본값(영어)로 설정합니다."),
    INVALID_NAVER_CLIENT_API("Naver API 값이 없습니다. .env 파일에 NAVER_CLIENT_ID와 NAVER_CLIENT_SECRET을 설정해주세요.");

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
