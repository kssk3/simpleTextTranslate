package woowacourse.textTranslate.swing.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import woowacourse.textTranslate.swing.domain.TargetText;

public class PapagoTranslationService implements TranslationService {

    private static final String API_URL = "https://papago.apigw.ntruss.com/nmt/v1/translation";
    private final OkHttpClient client;
    private final String clientId;
    private final String clientSecret;

    public PapagoTranslationService(String clientId, String clientSecret) {
        validate(clientId, clientSecret);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.client = new OkHttpClient();
    }

    @Override
    public TargetText translate(String koreanText, String targetLanguageCode) {
        // HTTP 요청 바디 생성
        FormBody requestBody = new FormBody.Builder()
                .add("source", "ko")
                .add("target", targetLanguageCode)
                .add("text", koreanText)
                .build();

        Request request = new Request.Builder()
                .url(API_URL)
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("API 호출 실패 : " + response.code());
            }

            String responseBody = response.body().string();
            // JSON 파싱
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

            String translatedText = jsonObject
                    .getAsJsonObject("message")
                    .getAsJsonObject("result")
                    .get("translatedText")
                    .getAsString();

            return new TargetText(translatedText);
        } catch (IOException e) {
            throw new RuntimeException("번역 API 통신 실패 : " + e.getMessage());
        }
    }

    private void validate(String clientId, String clientSecret) {
        if (clientId == null || clientId.isEmpty()) {
            throw new IllegalArgumentException("Client ID가 설정되지 않았습니다.");
        }
        if (clientSecret == null || clientSecret.isEmpty()) {
            throw new IllegalArgumentException("Client Secret이 설정되지 않았습니다.");
        }
    }
}
