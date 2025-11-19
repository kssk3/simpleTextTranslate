package woowacourse.textTranslate.controller;

import javax.swing.SwingUtilities;
import woowacourse.textTranslate.domain.ApplicationMode;
import woowacourse.textTranslate.domain.ModeSelector;
import woowacourse.textTranslate.domain.Translator;
import woowacourse.textTranslate.error.ErrorMessage;
import woowacourse.textTranslate.view.cli.controller.CliTranslateController;
import woowacourse.textTranslate.view.cli.view.TranslatorCLI;
import woowacourse.textTranslate.view.swing.controller.GuiTranslateController;
import woowacourse.textTranslate.view.swing.view.TranslatorGUI;

public class ApplicationController {

    private final Translator translator;
    private final ModeSelector modeSelector;

    public ApplicationController(Translator translator, ModeSelector modeSelector) {
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

