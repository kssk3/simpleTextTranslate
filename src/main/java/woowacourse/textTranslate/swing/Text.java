package woowacourse.textTranslate.swing;

import java.util.regex.Pattern;

public class Text {

    private final String REGEX = "^[ㄱ-ㅎㅏ-ㅣ가-힁\\s]*$";
    private final String text;

    public Text(String text) {
        validate(text);
        this.text = text;
    }

    private void validate(String text) {
        if(text == null || text.isEmpty()) {
            throw new IllegalArgumentException("입력된 값이 없습니다.");
        }

        if(!isKoreanRegex(text)) {
            throw new IllegalArgumentException("입력된 값이 한글이 아닙니다.");
        }
    }

    private boolean isKoreanRegex(String text) {
        Pattern pattern = Pattern.compile(REGEX);
        return pattern.matcher(text).matches();
    }

    public String getText() {
        return this.text;
    }
}
