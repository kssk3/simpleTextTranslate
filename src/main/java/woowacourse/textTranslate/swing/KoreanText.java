package woowacourse.textTranslate.swing;

public class KoreanText {

    private final String text;

    public KoreanText(String text) {
        validate(text);
        this.text = text;
    }

    private void validate(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("입력된 값이 없습니다.");
        }

        if (!KoreanRegex.isKoreanRegex(text)) {
            throw new IllegalArgumentException("입력된 값이 한글이 아닙니다.");
        }
    }

    public String getText() {
        return this.text;
    }
}
