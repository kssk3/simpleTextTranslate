package woowacourse.textTranslate.swing.controller;

import woowacourse.textTranslate.swing.domain.Translator;
import woowacourse.textTranslate.swing.view.TranslatorGUI;
import woowacourse.textTranslate.swing.domain.KoreanText;
import woowacourse.textTranslate.swing.domain.TargetText;

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
                translatorGUI.displayError("번역할 한글을 입력해주세요");
            }

            KoreanText koreanText = new KoreanText(inputText);

            TargetText translatedText = translator.translate(koreanText, translatorGUI.getTargetLanguage());

            translatorGUI.displayResult(translatedText.getTranslatedText());

        } catch (IllegalArgumentException e) {
            translatorGUI.displayError("입력 오류 : " + e.getMessage());
        } catch (Exception e) {
            translatorGUI.displayError("번역 실패 : " + e.getMessage());
        }

    }

    public void start() {
        translatorGUI.showGUI();
    }
}
