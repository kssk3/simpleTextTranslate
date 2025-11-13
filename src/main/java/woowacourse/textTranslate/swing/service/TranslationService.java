package woowacourse.textTranslate.swing.service;

@FunctionalInterface
public interface TranslationService {
    String translate(String koreanText, String targetLanguage);
}
