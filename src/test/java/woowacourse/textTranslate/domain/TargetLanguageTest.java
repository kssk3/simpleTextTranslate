package woowacourse.textTranslate.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TargetLanguageTest {

    @DisplayName("ENGLISH 코드가 en이면 통과")
    @Test
    void ENGLISH_코드가_en이면_통과() {
        // when
        TargetLanguage english = TargetLanguage.ENGLISH;

        // then
        assertEquals("en", english.getCode());
        assertEquals("영어", english.getDisplayName());
    }

    @DisplayName("모든 언어 enum 값이 존재한다")
    @Test
    void 모든_언어_enum_값이_존재한다() {
        // when
        TargetLanguage[] values = TargetLanguage.values();

        // then
        assertThat(values).hasSize(3);
        assertThat(values).containsExactlyInAnyOrder(
                TargetLanguage.ENGLISH,
                TargetLanguage.JAPANESE,
                TargetLanguage.CHINESE
        );
    }

    @DisplayName("존재하는 언어 이름으로 호출시 통과")
    @Test
    void 존재하는_언어_이름으로_호출시_통과() {
        // when
        TargetLanguage english = TargetLanguage.valueOf("ENGLISH");

        assertEquals("en", english.getCode());
        assertEquals("영어", english.getDisplayName());
    }

    @DisplayName("존재하지 않은 언어 이름으로 호출시 예외 발생")
    @Test
    void 존재하지_않은_언어_이름으로_호출시_예외_발생() {
        assertThatThrownBy(() -> TargetLanguage.valueOf("KOREAN"))
                .isInstanceOf(IllegalArgumentException.class);
    }


}