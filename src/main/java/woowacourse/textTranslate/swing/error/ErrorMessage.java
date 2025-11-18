package woowacourse.textTranslate.swing.error;

public enum ErrorMessage {
    INVALID_TEXT_INPUT("번역할 한글을 입력해주세요"),
    INVALID_CHOICE_LANGUAGE("잘못된 선택입니다. 기본값(영어)로 설정합니다.");

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
