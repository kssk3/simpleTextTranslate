package woowacourse.textTranslate.swing.service;

import woowacourse.textTranslate.swing.domain.TargetText;

@FunctionalInterface
public interface TranslationService {
    TargetText translate(String koreanText, String targetLanguage);
}
