package woowacourse.textTranslate;

import woowacourse.textTranslate.config.ApiKeyProvider;
import woowacourse.textTranslate.runner.ApplicationRunner;
import woowacourse.textTranslate.view.ModeSelector;
import woowacourse.textTranslate.config.TranslatorFactory;
import woowacourse.textTranslate.domain.Translator;

public class Application {

    public static void main(String[] args) {
        ApiKeyProvider apiKeyProvider = new ApiKeyProvider();

        TranslatorFactory translatorFactory = new TranslatorFactory(apiKeyProvider);
        Translator translator = translatorFactory.createTranslator();

        ModeSelector modeSelector = new ModeSelector();

        ApplicationRunner controller = new ApplicationRunner(translator, modeSelector);
        controller.run();
    }
}
