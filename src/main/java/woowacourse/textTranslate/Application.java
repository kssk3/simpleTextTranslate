package woowacourse.textTranslate;

import woowacourse.textTranslate.controller.ApiKeyProvider;
import woowacourse.textTranslate.controller.ApplicationController;
import woowacourse.textTranslate.domain.ModeSelector;
import woowacourse.textTranslate.domain.TranslatorFactory;
import woowacourse.textTranslate.domain.Translator;

public class Application {

    public static void main(String[] args) {
        ApiKeyProvider apiKeyProvider = new ApiKeyProvider();

        TranslatorFactory translatorFactory = new TranslatorFactory(apiKeyProvider);
        Translator translator = translatorFactory.createTranslator();

        ModeSelector modeSelector = new ModeSelector();
        modeSelector.close();

        ApplicationController controller = new ApplicationController(translator, modeSelector);
        controller.run();
    }
}
