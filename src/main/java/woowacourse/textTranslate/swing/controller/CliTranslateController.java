package woowacourse.textTranslate.swing.controller;

import woowacourse.textTranslate.swing.domain.Translator;
import woowacourse.textTranslate.swing.view.TranslatorCLI;

public class CliTranslateController {

    private final Translator translator;
    private final TranslatorCLI translatorCLI;

    public CliTranslateController(Translator translator, TranslatorCLI translatorCLI) {
        this.translator = translator;
        this.translatorCLI = translatorCLI;
    }

    public void showWellComeMessage() {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║   한글-영어 번역기 (CLI 버전)    ║");
        System.out.println("╚═══════════════════════════════════╝");
        System.out.println();
    }
}
