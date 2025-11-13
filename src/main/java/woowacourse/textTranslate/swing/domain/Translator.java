package woowacourse.textTranslate.swing.domain;

import woowacourse.textTranslate.swing.service.TranslationService;

public class Translator {

    private final TranslationService translationService;

    public Translator(TranslationService translationService) {
        this.translationService = translationService;
    }

    public String translate(KoreanText koreanText, TargetText targetText) {
        String inputText = koreanText.getText();
        String targetLanguage = targetText.getLanguage();

        return translationService.translate(inputText, targetLanguage);
    }
}
