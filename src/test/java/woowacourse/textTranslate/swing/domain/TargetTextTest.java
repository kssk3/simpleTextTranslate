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
        assertNotNull(targetText);
        assertEquals(targetText.getTranslatedText(), "English");
    }

    @DisplayName("입력된 값이 한글이 아니면 통과")
    @Test
    void 입력된_값이_한글이_아니면_통과() {
        final TargetText targetText = new TargetText("おはよう");
        assertThat(targetText).isNotNull();
        assertEquals(targetText.getTranslatedText(), "おはよう");
    }

    @DisplayName("입력된 값이 한글이면 예외를 던진다")
    @Test
    void 입력된_값이_한글이면_예외를_던진다() {
        assertThatThrownBy(() -> new TargetText("한글"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숫자_특수문자가_모두_포함된_입력은_통과")
    @Test
    void 숫자_특수문자가_모두_포홤된_입력은_통과() {
        final TargetText targetText = new TargetText("English_123");
        assertThat(targetText).isNotNull();
        assertThat(targetText.getTranslatedText()).isEqualTo("English_123");
    }
}