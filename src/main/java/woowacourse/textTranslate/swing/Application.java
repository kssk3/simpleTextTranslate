package woowacourse.textTranslate.swing;

import io.github.cdimascio.dotenv.Dotenv;
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

        // 2. .env 파일에서 읽기 시도 (dotenv 라이브러리 사용)
        try {
            String userDir = System.getProperty("user.dir");
            // 여러 경로 시도
            String[] paths = {
                    ".",  // 프로젝트 루트
                    userDir,  // 절대 경로
            };

            for (String path : paths) {
                try {
                    System.out.println(path + "/.env 파일 찾는 중...");
                    Dotenv dotenv = Dotenv.configure()
                            .directory(path)
                            .ignoreIfMissing()
                            .load();

                    apiKey = dotenv.get(keyName);
                    if (apiKey != null && !apiKey.isEmpty()) {
                        return apiKey;
                    }
                } catch (Exception e) {
                    System.out.println(path + " 경로 실패: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(".env 파일 읽기 실패: " + e.getMessage());
            e.printStackTrace();
        }

        System.err.println(keyName + " 을(를) 찾을 수 없습니다.");
        return null;
    }
}
