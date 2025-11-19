package woowacourse.textTranslate;

import java.util.Scanner;
import woowacourse.textTranslate.error.ErrorMessage;

public class ModeSelector {

    private static final String CLI_MODE = "1";
    private static final String GUI_MODE = "2";

    private final Scanner scanner;

    public ModeSelector() {
        this.scanner = new Scanner(System.in);
    }

    public ApplicationMode selectMode() {
        displayModeOptions();
        String input = this.scanner.nextLine().trim();

        return parseModeInput(input);
    }
    
    private void displayModeOptions() {
        System.out.println("==============================");
        System.out.println("    번역기 실행 모드 선택");
        System.out.println("==============================");
        System.out.println("1. CLI 모드 (콘솔 기반)");
        System.out.println("2. GUI 모드 (그래픽 기반)");
        System.out.println("==============================");
        System.out.print("모드를 선택하세요 (1 또는 2): ");
    }

    private ApplicationMode parseModeInput(String input) {
        if(input.equals(CLI_MODE)) {
            return ApplicationMode.CLI;
        }
        if (input.equals(GUI_MODE)) {
            return ApplicationMode.GUI;
        }

        System.out.println(ErrorMessage.INVALID_CHOICE_MODE_SELECTOR.getMessage());
        return ApplicationMode.CLI;
    }

    public void close() {
        if(this.scanner != null) {
            this.scanner.close();
        }
    }
}
