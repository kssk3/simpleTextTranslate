package woowacourse.textTranslate.domain;

import woowacourse.textTranslate.error.ErrorMessage;

public class TargetText {

    private final String translatedText;

    public TargetText(String translatedText) {
        validate(translatedText);
        this.translatedText = translatedText;
    }

    private void validate(String translatedText) {
        if (translatedText == null || translatedText.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_TEXT_INPUT.getMessage());
        }

        if (KoreanRegex.isKoreanRegex(translatedText)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_TRANSLATION_FAILED.getMessage());
        }
    }

    public String getTranslatedText() {
        return this.translatedText.trim();
    }

}
