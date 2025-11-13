package woowacourse.textTranslate.swing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.SwingUtilities;
import woowacourse.textTranslate.swing.controller.TranslateController;
import woowacourse.textTranslate.swing.domain.Translator;
import woowacourse.textTranslate.swing.service.PapagoTranslationService;
import woowacourse.textTranslate.swing.view.TranslatorGUI;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String clientId = loadApiKey("NAVER_CLIENT_ID");
            String clientSecret = loadApiKey("NAVER_CLIENT_SECRET");

            if (clientId == null || clientId.isEmpty() || clientSecret == null || clientSecret.isEmpty()) {
                throw new RuntimeException("Naver API 값이 없습니다. .env 파일에 NAVER_CLIENT_ID와 NAVER_CLIENT_SECRET을 설정해주세요.");
            }

            PapagoTranslationService papagoTranslationService = new PapagoTranslationService(clientId, clientSecret);
            Translator translator = new Translator(papagoTranslationService);
            TranslateController translateController = new TranslateController(translator, new TranslatorGUI());
            translateController.start();
        });
    }

    private static String loadApiKey(String keyName) {
        // 1. 환경 변수에서 읽기 시도
        String apiKey = System.getenv(keyName);
        if (apiKey != null && !apiKey.isEmpty()) {
            return apiKey;
        }

        // 2. .env 파일에서 읽기 시도
        apiKey = loadFromEnvFile(keyName);
        if (apiKey != null && !apiKey.isEmpty()) {
            return apiKey;
        }

        return null;
    }

    private static String loadFromEnvFile(String keyName) {
        try (InputStream inputStream = Application.class.getClassLoader().getResourceAsStream(".env")) {
            if (inputStream == null) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith(keyName + "=")) {
                    String value = line.substring((keyName + "=").length()).trim();
                    // 따옴표 제거
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    return value;
                }
            }
        } catch (Exception e) {
            System.err.println(".env 파일 읽기 실패: " + e.getMessage());
        }
        return null;
    }
}
