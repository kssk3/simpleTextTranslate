package woowacourse.textTranslate.runner;

import javax.swing.SwingUtilities;
import woowacourse.textTranslate.view.ApplicationMode;
import woowacourse.textTranslate.view.ModeSelector;
import woowacourse.textTranslate.domain.Translator;
import woowacourse.textTranslate.view.cli.controller.CliTranslateController;
import woowacourse.textTranslate.view.cli.view.TranslatorCLI;
import woowacourse.textTranslate.view.swing.controller.GuiTranslateController;
import woowacourse.textTranslate.view.swing.view.TranslatorGUI;

public class ApplicationRunner {

    private final Translator translator;
    private final ModeSelector modeSelector;

    public ApplicationRunner(Translator translator, ModeSelector modeSelector) {
        this.translator = translator;
        this.modeSelector = modeSelector;
    }

    public void run() {
        ApplicationMode mode = modeSelector.selectMode();

        if (mode == ApplicationMode.CLI) {
            runCliMode();
        }
        if (mode == ApplicationMode.GUI) {
            runGuiMode();
        }
    }

    private void runCliMode() {
        CliTranslateController cliTranslateController = new CliTranslateController(this.translator,
                new TranslatorCLI());
        cliTranslateController.start();
    }

    private void runGuiMode() {
        SwingUtilities.invokeLater(() -> {
            GuiTranslateController translateController = new GuiTranslateController(this.translator,
                    new TranslatorGUI());
            translateController.start();
        });
    }
}

