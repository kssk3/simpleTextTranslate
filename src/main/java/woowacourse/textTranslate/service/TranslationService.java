package woowacourse.textTranslate.service;

import woowacourse.textTranslate.domain.TargetText;

@FunctionalInterface
public interface TranslationService {
    TargetText translate(String koreanText, String targetLanguage);
}
