package woowacourse.textTranslate;

import javax.swing.SwingUtilities;
import woowacourse.textTranslate.controller.ApiKeyProvider;
import woowacourse.textTranslate.domain.TranslatorFactory;
import woowacourse.textTranslate.view.cli.controller.CliTranslateController;
import woowacourse.textTranslate.view.swing.controller.GuiTranslateController;
import woowacourse.textTranslate.domain.Translator;
import woowacourse.textTranslate.view.cli.view.TranslatorCLI;
import woowacourse.textTranslate.view.swing.view.TranslatorGUI;

public class Application {

    public static void main(String[] args) {
        ApiKeyProvider apiKeyProvider = new ApiKeyProvider();

        TranslatorFactory translatorFactory = new TranslatorFactory(apiKeyProvider);
        Translator translator = translatorFactory.createTranslator();

        
    }

    private static void runCliMode(Translator translator) {
        CliTranslateController cliTranslateController = new CliTranslateController(translator, new TranslatorCLI());
        cliTranslateController.start();
    }

    private static void runGuiMode(Translator translator) {
        SwingUtilities.invokeLater(() -> {
            GuiTranslateController translateController = new GuiTranslateController(translator, new TranslatorGUI());
            translateController.start();
        });
    }

}
