package woowacourse.textTranslate.swing;

import java.util.regex.Pattern;

public final class KoreanRegex {

    private static final String REGEX = "^[ㄱ-ㅎㅏ-ㅣ가-힁\\s]*$";

    public static boolean isKoreanRegex(String text) {
        return Pattern.compile(REGEX).matcher(text).matches();
    }

    private KoreanRegex() {}
}
