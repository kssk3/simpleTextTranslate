package woowacourse.textTranslate.swing.view;

import java.util.Scanner;
import org.jetbrains.annotations.NotNull;
import woowacourse.textTranslate.swing.domain.TargetLanguage;
import woowacourse.textTranslate.swing.error.ErrorMessage;

public class TranslatorCLI {

    private final Scanner scanner;

    public TranslatorCLI() {
        this.scanner = new Scanner(System.in);
    }

    public void showWellComeMessage() {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║   한글-영어 번역기 (CLI 버전)    ║");
        System.out.println("╚═══════════════════════════════════╝");
        System.out.println();
    }

    public TargetLanguage getTargetLanguage() {
        System.out.println("번역할 언어를 선택하세요:");
        System.out.println("1. 영어 (English)");
        System.out.println("2. 일본어 (Japanese)");
        System.out.println("3. 중국어 (Chinese)");
        System.out.print("선택 (1 ~ 3) ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 버퍼 지우기

        return getTargetLanguage(choice);
    }

    private static TargetLanguage getTargetLanguage(int choice) {
        return switch (choice) {
            case 1 -> TargetLanguage.ENGLISH;
            case 2 -> TargetLanguage.JAPANESE;
            case 3 -> TargetLanguage.CHINESE;
            default -> {
                System.out.println(ErrorMessage.INVALID_CHOICE_LANGUAGE.getMessage());
                yield TargetLanguage.ENGLISH;
            }
        };
    }

    public String getInputText() {
        System.out.println("번역할 한글 텍스트를 입력하세요: ");
        return scanner.nextLine().trim();
    }

    public void displayError(String errorMessage) {
        System.out.println("오류 : " + errorMessage);
        System.err.println();
    }

    public void displayResult(String translatedText) {
        System.out.println("\n─────────────────────────────────");
        System.out.println("📝 번역 결과:");
        System.out.println(translatedText);
        System.out.println("─────────────────────────────────\n");
    }
}
