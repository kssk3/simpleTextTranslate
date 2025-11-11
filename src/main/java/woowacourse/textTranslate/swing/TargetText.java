package woowacourse.textTranslate.swing;

public class TargetText {

    private final String text;

    public TargetText(String text) {
        validate(text);
        this.text = text;
    }

    private void validate(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("입력된 값이 없습니다.");
        }

        if(KoreanRegex.isKoreanRegex(text)) {
            throw new IllegalArgumentException("한글을 입력 받을 수 없습니다.");
        }
    }

    public String getText() {
        return this.text;
    }
}
