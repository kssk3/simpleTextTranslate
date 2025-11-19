package woowacourse.textTranslate.domain;

public class TargetText {

    private final String translatedText;

    public TargetText(String translatedText) {
        validate(translatedText);
        this.translatedText = translatedText;
    }

    private void validate(String translatedText) {
        if (translatedText == null || translatedText.isEmpty()) {
            throw new IllegalArgumentException("번역된 텍스트가 비어있습니다.");
        }

        if (KoreanRegex.isKoreanRegex(translatedText)) {
            throw new IllegalArgumentException("한국어로 번역할 수 없습니다.");
        }
    }

    public String getTranslatedText() {
        return this.translatedText.trim();
    }

}
