package woowacourse.textTranslate.config;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.textTranslate.domain.Translator;

class TranslatorFactoryTest {

    @DisplayName("Factory가 Translator를 정상적으로 생성하면 테스트 통과")
    @Test
    void Translator_생성시_테스트_통과() {
        // given
        final FakeApiKeyProvider apiKeyProvider = new FakeApiKeyProvider("fake-id", "fake-secret");
        final TranslatorFactory factory = new TranslatorFactory(apiKeyProvider);

        // when
        Translator translator = factory.createTranslator();

        // then
        assertThat(translator).isNotNull();
    }

    @DisplayName("null ApiKeyProvider로 Factory 생성시 예외 발생")
    @Test
    void null_ApiKeyProvider_생성시_예외() {
        assertThatThrownBy(() -> new TranslatorFactory(null))
                .isInstanceOf(NullPointerException.class);
    }


    private static class FakeApiKeyProvider extends ApiKeyProvider {
        private final String clientId;
        private final String clientSecret;

        public FakeApiKeyProvider(String clientId, String clientSecret) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }

        public String getClientId() {
            return clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }
    }
}