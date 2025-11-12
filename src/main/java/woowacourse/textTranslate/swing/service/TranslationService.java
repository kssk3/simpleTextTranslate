package woowacourse.textTranslate.swing.service;

import woowacourse.textTranslate.swing.domain.KoreanText;
import woowacourse.textTranslate.swing.domain.TargetText;

public interface TranslationService {
    String translate(KoreanText koreanText, TargetText targetLanguage);
}
