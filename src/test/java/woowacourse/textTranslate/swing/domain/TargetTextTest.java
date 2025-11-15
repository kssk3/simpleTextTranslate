package woowacourse.textTranslate.swing.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TargetTextTest {

    @DisplayName("입력된 값이 영어가 맞으면 통과")
    @Test
    void 입력된_값이_영어가_맞으면_통과() {
        final TargetText targetText = new TargetText("English");
        assertThat(targetText).isNotNull();
        assertEquals(targetText.getLanguage(), "English");
    }

    @DisplayName("입력된 값이 영어가 아니면 예외를 던진다")
    @Test
    void 입력된_값이_영어가_아니면_예외를_던진다() {
        assertThatThrownBy(() -> new TargetText("한글"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}