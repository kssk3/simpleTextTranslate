package woowacourse.textTranslate.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import woowacourse.textTranslate.domain.TargetText;

public class KakaoTranslationService implements TranslationService {

    private final String apiKey;
    private final String apiUrl;
    private final OkHttpClient client;

    public KakaoTranslationService(String apiKey) {
        this(apiKey, "https://dapi.kakao.com/v2/translation/translate", new OkHttpClient());
    }

    public KakaoTranslationService(String apiKey, String apiUrl, OkHttpClient client) {
        validate(apiKey);
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.client = client;
    }

    @Override
    public TargetText translate(String koreanText, String targetLanguageCode) {
        // Http 요청 바디 생성
        FormBody requestBody = new Builder()
                .add("src_lang", "ko")
                .add("target_lang", targetLanguageCode)
                .add("query", koreanText)
                .build();

        Request request = new Request.Builder()
                .url(this.apiUrl)
                .header("Authorization", "KakaoAK=" + apiKey)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("API 호출 실패 : " + response.code());
            }

            String responseBody = response.body().string();
            // Json 파싱
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

            String translatedText = jsonObject.getAsJsonArray("translated_text")
                    .get(0)
                    .getAsJsonArray()
                    .get(0)
                    .getAsJsonObject()
                    .get("translated_text")
                    .getAsString();

            return new TargetText(translatedText);
        } catch (IOException e) {
            throw new RuntimeException("번역 API 통신 실패 : " + e.getMessage());
        }
    }

    private void validate(String api) {
        if (api == null || api.isEmpty()) {
            throw new IllegalArgumentException("API 키가 설정되지 않았습니다.");
        }
    }
}
