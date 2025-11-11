package woowacourse.textTranslate.swing;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TargetTextTest {

    @DisplayName("입력된 값이 한글이 아니면 테스트 통과")
    @Test
    void 입력된_값이_한글이_아니면_테스트_통과() {
        final TargetText english = new TargetText("English");
        assertEquals(english.getText(), "English");
    }

    @DisplayName("입력된 값이 한글이면 예외를 던진다")
    @Test
    void 입력된_값이_한글이면_예외를_던진다() {
        assertThatThrownBy(() -> new TargetText("한글"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
