package woowacourse.textTranslate.domain;

import woowacourse.textTranslate.controller.ApiKeyProvider;
import woowacourse.textTranslate.service.PapagoTranslationService;

public class TranslatorFactory {

    private final ApiKeyProvider apiKeyProvider;

    public TranslatorFactory(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    public Translator createTranslator() {
        String clientId = apiKeyProvider.getClientId();
        String clientSecret = apiKeyProvider.getClientSecret();
        return new Translator(new PapagoTranslationService(clientId, clientSecret));
    }
}
