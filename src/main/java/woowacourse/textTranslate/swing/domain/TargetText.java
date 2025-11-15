package woowacourse.textTranslate.swing.domain;

public class TargetText {

    private final String englishText;

    public TargetText(String englishText) {
        validate(englishText);
        this.englishText = englishText;
    }

    private void validate(String englishText) {
        if (englishText == null || englishText.isEmpty()) {
            throw new IllegalArgumentException("번역할 언어를 입력해주세요");
        }

        if (KoreanRegex.isKoreanRegex(englishText)) {
            throw new IllegalArgumentException("한국어로 번역할 수 없습니다.");
        }
    }

    public String getLanguage() {
        return englishText;
    }
}
