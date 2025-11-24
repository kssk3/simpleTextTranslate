package woowacourse.textTranslate.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.textTranslate.domain.KoreanText;

class KoreanTextTest {

    @DisplayName("입력된 값이 한글이 맞음면 통과")
    @Test
    void 입력된_값이_한글이_맞으면_통과() {
        final KoreanText korean = new KoreanText("한글");
        assertThat(korean).isNotNull();
        assertEquals(korean.getText(), "한글");
    }

    @DisplayName("입력된 값이 한글이 아니면 예외를 던진다")
    @Test
    void 입력된_값이_한글이_아니면_예외를_던진다() {
        assertThatThrownBy(() -> new KoreanText("English"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("한글과 특수문자가 포함된 입력은 통과")
    @Test
    void 한글과_특수문자가_포함된_입력은_통과() {
        final KoreanText korean = new KoreanText("당신의 이름은 무엇입니까?");
        assertThat(korean).isNotNull();
        assertEquals(korean.getText(), "당신의 이름은 무엇입니까?");
    }

    @DisplayName("한글과 숫자가 포함된 입력은 통과")
    @Test
    void 한글과_숫자가_포함된_입력은_통과() {
        final KoreanText korean = new KoreanText("나는 20살입니다");
        assertThat(korean).isNotNull();
        assertEquals(korean.getText(), "나는 20살입니다");
    }

    @DisplayName("한글, 숫자, 특수문자가 모두 포함된 입력은 통과")
    @Test
    void 한글_숫자_특수문자가_모두_포함된_입력은_통과() {
        final KoreanText korean = new KoreanText("오늘은 2025년 1월 1일입니다!");
        assertThat(korean).isNotNull();
        assertEquals(korean.getText(), "오늘은 2025년 1월 1일입니다!");
    }

    @DisplayName("다양한 특수문자가 포함된 입력은 통과")
    @Test
    void 다양한_특수문자가_포함된_입력은_통과() {
        final KoreanText korean = new KoreanText("안녕하세요! (반갑습니다) \"좋은 하루\", #해시태그");
        assertThat(korean).isNotNull();
        assertEquals(korean.getText(), "안녕하세요! (반갑습니다) \"좋은 하루\", #해시태그");
    }
}
