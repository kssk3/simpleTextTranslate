package woowacourse.textTranslate.swing.controller;

import woowacourse.textTranslate.swing.TranslatorGUI;
import woowacourse.textTranslate.swing.domain.KoreanText;
import woowacourse.textTranslate.swing.domain.TargetText;
import woowacourse.textTranslate.swing.service.TranslationService;

public class TranslateController {

    private final TranslationService translationService;
    private final TranslatorGUI translatorGUI;

    public TranslateController(TranslationService translationService, TranslatorGUI translatorGUI) {
        this.translationService = translationService;
        this.translatorGUI = translatorGUI;
    }

    private void handleTranslateButtonClick() {
        try {
            String inputText = translatorGUI.getInputText();

            if (inputText == null || inputText.isEmpty()) {
                translatorGUI.displayError("번역할 한글을 입력해주세요");
            }

            KoreanText koreanText = new KoreanText(inputText);
            TargetText targetLanguage = new TargetText("English");

            String translatedText = translationService.translate(koreanText, targetLanguage);

            translatorGUI.displayResult(translatedText);

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
