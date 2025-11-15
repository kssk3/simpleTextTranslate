package woowacourse.textTranslate.swing.domain;

public class KoreanText {

    private final String koreanText;

    public KoreanText(String koreanText) {
        validate(koreanText);
        this.koreanText = koreanText;
    }

    private void validate(String koreanText) {
        if (koreanText == null || koreanText.isEmpty()) {
            throw new IllegalArgumentException("입력된 값이 없습니다.");
        }

        if (!KoreanRegex.isKoreanRegex(koreanText)) {
            throw new IllegalArgumentException("입력된 값이 한글이 아닙니다.");
        }
    }

    public String getText() {
        return this.koreanText;
    }
}
