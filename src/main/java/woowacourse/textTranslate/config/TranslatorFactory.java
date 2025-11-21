package woowacourse.textTranslate.config;

import woowacourse.textTranslate.domain.Translator;
import woowacourse.textTranslate.service.PapagoTranslationService;
import woowacourse.textTranslate.service.TranslationService;

public class TranslatorFactory {

    private final ApiKeyProvider apiKeyProvider;

    public TranslatorFactory(ApiKeyProvider apiKeyProvider) {
        validate(apiKeyProvider);
        this.apiKeyProvider = apiKeyProvider;
    }

    public Translator createTranslator() {
        String clientId = apiKeyProvider.getClientId();
        String clientSecret = apiKeyProvider.getClientSecret();
        return new Translator(new PapagoTranslationService(clientId, clientSecret));
    }

    private void validate(ApiKeyProvider apiKeyProvider) {
        if(apiKeyProvider == null) {
            throw new NullPointerException("apiKeyProvider is null");
        }
    }
}
