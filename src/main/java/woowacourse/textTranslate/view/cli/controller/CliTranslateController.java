package woowacourse.textTranslate.view.cli.controller;

import woowacourse.textTranslate.domain.KoreanText;
import woowacourse.textTranslate.domain.TargetText;
import woowacourse.textTranslate.domain.Translator;
import woowacourse.textTranslate.view.TranslatorView;
import woowacourse.textTranslate.view.cli.view.TranslatorCLI;

public class CliTranslateController {

    private final Translator translator;
    private final TranslatorView view;

    public CliTranslateController(Translator translator, TranslatorView view) {
        this.translator = translator;
        this.view = view;
    }

    public void start() {
        view.showWellComeMessage();

        boolean shouldContinue = true;
        while (shouldContinue) {
            try {
                handleTranslation();
            } catch (IllegalArgumentException e) {
                view.displayError(e.getMessage());
            }
            shouldContinue = view.askContinue();
        }

        view.close();
    }

    private void handleTranslation() {
        String inputText = view.getInputText();
        KoreanText koreanText = new KoreanText(inputText);

        TargetText translatedText = translator.translate(koreanText, view.getTargetLanguage());

        view.displayResult(translatedText.getTranslatedText());
    }
}
