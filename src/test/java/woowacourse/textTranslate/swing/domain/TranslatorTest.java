package woowacourse.textTranslate.swing.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.textTranslate.swing.service.TranslationService;

class TranslatorTest {

    /**
     * Spring Boot의 Mock을 사용하지 않고 람다로 테스트하기
     */
    @DisplayName("번역될 언어가 일본어면 통과")
    @Test
    void 한국어를_일본어로_정상_번역() {
        // given
        TranslationService mockService = (text, lang) -> {
            assertThat(text).isEqualTo("안녕하세요");
            assertThat(lang).isEqualTo("ja");
            return new TargetText("こんにちは");
        };
        Translator translator = new Translator(mockService);
        KoreanText koreanText = new KoreanText("안녕하세요");

        // when
        TargetText result = translator.translate(koreanText, TargetLanguage.JAPANESE);

        // then
        assertThat(result.getTranslatedText()).isEqualTo("こんにちは");
    }

    @DisplayName("한국어가 아닌 입력값으로 번역 시도하면 예외 발생")
    @Test
    void 한국어가_아닌_입력값으로_번역_시도하면_예외_발생() {
        // given
        TranslationService mockService = (text, lang) -> {
            return new TargetText("Hello");
        };

        // when
        Translator translator = new Translator(mockService);

        //then
        assertThatThrownBy(() ->
                translator.translate(new KoreanText("こんにちは"), TargetLanguage.ENGLISH))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
