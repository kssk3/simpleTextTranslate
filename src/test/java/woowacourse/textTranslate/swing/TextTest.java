package woowacourse.textTranslate.swing;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TextTest {

    @DisplayName("입력된 값이 한글이 맞음면 통과")
    @Test
    void 입력된_값이_한글이_맞으면_통과() {
        final Text korean = new Text("한글");
        assertThat(korean).isNotNull();
        assertEquals(korean.getText(), "한글");
    }

    @DisplayName("입력된 값이 한글이 아니면 예외를 던진다")
    @Test
    void 입력된_값이_한글이_아니면_예외를_던진다() {
        assertThatThrownBy(() -> new Text("English"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}