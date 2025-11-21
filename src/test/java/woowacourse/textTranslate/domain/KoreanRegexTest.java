package woowacourse.textTranslate.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KoreanRegexTest {

    @DisplayName("한글만 있으면 true 반환")
    @Test
    void 한글만_있으면_true_반환() {
        // given
        String korean = "안녕하십니까";

        // when
        boolean result = KoreanRegex.isKoreanRegex(korean);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("한글과 빈 공백이 포함되면 true 반환")
    @Test
    void 한글과_빈_공백이_포함되면_true_반환() {
        // given
        String korean = "안녕하세요 반값습니다";

        // when
        boolean result = KoreanRegex.isKoreanRegex(korean);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("한글과 빈 공백 숫자 특수문자 포함되면 true 반환")
    @Test
    void 한글과_빈_공백_숫자_특수문자_포함되면_true_반환() {
        String text = "안녕하세요? 반값습니다 저는 00세 입니다.";

        boolean result = KoreanRegex.isKoreanRegex(text);

        assertTrue(result);
    }

    @DisplayName("자음과 모음만 있어도 true 반환")
    @Test
    void 자음과_모음만_있어도_true_반환() {
        String consonantVowel = "ㄱ ㄴ ㄷ ㅏ ㅗ ㅜ";

        boolean result = KoreanRegex.isKoreanRegex(consonantVowel);

        assertThat(result).isTrue();
    }

    @DisplayName("한글외 다른 언어가 포함되면 false 반환")
    @Test
    void 한글외_다른_언어가_포함되면_false_반환() {
        String english = "Hello World!";
        String japanese = "こんにちは";
        String chinese = "你好";

        boolean englishResult = KoreanRegex.isKoreanRegex(english);
        boolean japaneseResult = KoreanRegex.isKoreanRegex(japanese);
        boolean chineseResult = KoreanRegex.isKoreanRegex(chinese);

        assertFalse(englishResult);
        assertFalse(japaneseResult);
        assertFalse(chineseResult);
    }
}

