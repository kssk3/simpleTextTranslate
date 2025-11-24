package woowacourse.textTranslate.domain;

import woowacourse.textTranslate.error.ErrorMessage;

public class KoreanText {

    private final String koreanText;

    public KoreanText(String koreanText) {
        validate(koreanText);
        this.koreanText = koreanText;
    }

    private void validate(String koreanText) {
        if (koreanText == null || koreanText.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_TEXT_INPUT.getMessage());
        }

        if (!KoreanRegex.isKoreanRegex(koreanText)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NOT_KOREAN_TEXT_INPUT.getMessage());
        }
    }

    public String getText() {
        return this.koreanText.trim();
    }
}
