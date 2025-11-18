package woowacourse.textTranslate.swing.controller;

import woowacourse.textTranslate.swing.domain.KoreanText;
import woowacourse.textTranslate.swing.domain.TargetText;
import woowacourse.textTranslate.swing.domain.Translator;
import woowacourse.textTranslate.swing.error.ErrorMessage;
import woowacourse.textTranslate.swing.view.TranslatorCLI;

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

        if (inputText == null || inputText.isEmpty()) {
            translatorCLI.displayError(ErrorMessage.INVALID_TEXT_INPUT.getMessage());
        }

        KoreanText koreanText = new KoreanText(inputText);

        TargetText translatedText = translator.translate(koreanText, translatorCLI.getTargetLanguage());

        translatorCLI.displayResult(translatedText.getTranslatedText());
    }
}
