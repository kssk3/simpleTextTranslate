package woowacourse.textTranslate;

import io.github.cdimascio.dotenv.Dotenv;
import woowacourse.textTranslate.error.ErrorMessage;

public class ApiKeyProvider {

    private final String clientId;
    private final String clientSecret;;

    public ApiKeyProvider() {
        this.clientId = loadApiKey("NAVER_CLIENT_ID");
        this.clientSecret = loadApiKey("NAVER_CLIENT_SECRET");
        validateApiKey(clientId, clientSecret);
    }

    private static void validateApiKey(String clientId, String clientSecret) {
        if (clientId == null || clientId.isEmpty() || clientSecret == null || clientSecret.isEmpty()) {
            throw new RuntimeException(ErrorMessage.INVALID_NAVER_CLIENT_API.getMessage());
        }
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

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
