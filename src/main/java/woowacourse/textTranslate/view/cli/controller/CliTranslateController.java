package woowacourse.textTranslate.view.cli.controller;

import woowacourse.textTranslate.domain.KoreanText;
import woowacourse.textTranslate.domain.TargetText;
import woowacourse.textTranslate.domain.Translator;
import woowacourse.textTranslate.view.cli.view.TranslatorCLI;

public class CliTranslateController {

    private final Translator translator;
    private final TranslatorCLI translatorCLI;

    public CliTranslateController(Translator translator, TranslatorCLI translatorCLI) {
        this.translator = translator;
        this.translatorCLI = translatorCLI;
    }

    public void start() {
        translatorCLI.showWellComeMessage();

        boolean shouldContinue = true;
        while (shouldContinue) {
            try {
                handleTranslation();
            } catch (IllegalArgumentException e) {
                translatorCLI.displayError(e.getMessage());
            }
            shouldContinue = translatorCLI.askContinue();
        }

        translatorCLI.close();
    }

    private void handleTranslation() {
        String inputText = translatorCLI.getInputText();
        KoreanText koreanText = new KoreanText(inputText);

        TargetText translatedText = translator.translate(koreanText, translatorCLI.getTargetLanguage());

        translatorCLI.displayResult(translatedText.getTranslatedText());
    }
}
