package woowacourse.textTranslate.view.cli.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.textTranslate.domain.TargetLanguage;
import woowacourse.textTranslate.domain.TargetText;
import woowacourse.textTranslate.domain.Translator;
import woowacourse.textTranslate.service.TranslationService;
import woowacourse.textTranslate.view.cli.view.TranslatorCLI;

class CliTranslateControllerTest {

    private Translator translator;

    @BeforeEach
    void setUp() {
        TranslationService fakeService = (text, lang) -> new TargetText("Hello");
        translator = new Translator(fakeService);
    }

    @Test
    void 정상적으로_텍스트가_번역될_경우_통과() {
        // given
        final List<String> results = new ArrayList<>();
        final List<String> errors = new ArrayList<>();

        final TranslatorCLI cli = new TranslatorCLI(){
            public String getInputText() {return "안녕하세요";}
            public TargetLanguage getTargetLanguage() {return TargetLanguage.ENGLISH;}
            public void displayResult(String result) {results.add(result);}
            public boolean askContinue() {return false;}
        };

        // when
        CliTranslateController controller = new CliTranslateController(this.translator, cli);
        controller.start();

        // then
        assertThat(results).isNotNull();
        assertThat(results).contains("Hello");
    }

    @DisplayName("정상적으로 텍스트가 번역 되지 않을 경우 예외 발생")
    @Test
    void 정삭적으로_텍스트가_번역_되지_않을_경우_예외_발생() {
        // given
        final List<String> results = new ArrayList<>();
        final List<String> errors = new ArrayList<>();

        final TranslatorCLI cli = new TranslatorCLI(){
            public String getInputText() {return "Hello";}
            public void displayError(String error) {errors.add(error);}
            public boolean askContinue() {return false;}
        };

        // when
        CliTranslateController controller = new CliTranslateController(this.translator, cli);
        controller.start();

        assertThat(results).isEmpty();
        assertThat(errors).isNotNull();
        assertThat(errors.get(0)).contains("한글");
    }
}