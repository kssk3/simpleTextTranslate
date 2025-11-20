package woowacourse.textTranslate.view.swing.controller;

import woowacourse.textTranslate.domain.Translator;
import woowacourse.textTranslate.error.ErrorMessage;
import woowacourse.textTranslate.view.swing.view.TranslatorGUI;
import woowacourse.textTranslate.domain.KoreanText;
import woowacourse.textTranslate.domain.TargetText;

public class GuiTranslateController {

    private final Translator translator;
    private final TranslatorGUI translatorGUI;

    public GuiTranslateController(Translator translator, TranslatorGUI translatorGUI) {
        this.translator = translator;
        this.translatorGUI = translatorGUI;

        translatorGUI.setTranslateButtonListener(this::handleTranslateButtonClick);
    }

    private void handleTranslateButtonClick() {
        try {
            String inputText = translatorGUI.getInputText();

            if (inputText == null || inputText.isEmpty()) {
                translatorGUI.displayError(ErrorMessage.INVALID_TEXT_INPUT.getMessage());
            }

            KoreanText koreanText = new KoreanText(inputText);

            TargetText translatedText = translator.translate(koreanText, translatorGUI.getTargetLanguage());

            translatorGUI.displayResult(translatedText.getTranslatedText());

        } catch (IllegalArgumentException e) {
            translatorGUI.displayError(ErrorMessage.INVALID_TEXT_INPUT.getMessage());
        } catch (Exception e) {
            translatorGUI.displayError(ErrorMessage.INVALID_TRANSLATION_FAILED.getMessage());
        }

    }

    public void start() {
        translatorGUI.showGUI();
    }
}
