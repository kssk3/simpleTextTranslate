package woowacourse.textTranslate.swing.domain;

import java.util.regex.Pattern;

public final class KoreanRegex {

    // 한글, 숫자, 특수문자, 공백을 모두 허용
    private static final String REGEX = "^[ㄱ-ㅎㅏ-ㅣ가-힁0-9\\s\\p{Punct}]*$";

    public static boolean isKoreanRegex(String text) {
        return Pattern.compile(REGEX).matcher(text).matches();
    }

    private KoreanRegex() {}
}
