package woowacourse.textTranslate.swing.domain;

import woowacourse.textTranslate.swing.service.TranslationService;

public class Translator {

    private final TranslationService translationService;

    public Translator(TranslationService translationService) {
        this.translationService = translationService;
    }

    public TargetText translate(KoreanText koreanText, TargetLanguage targetLanguage) {
        String inputText = koreanText.getText();
        String targetLanguageCode = targetLanguage.getCode();

        return translationService.translate(inputText, targetLanguageCode);
    }
}
