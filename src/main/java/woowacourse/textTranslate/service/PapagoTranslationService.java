package woowacourse.textTranslate.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import woowacourse.textTranslate.domain.TargetText;

public class PapagoTranslationService implements TranslationService {

    private static final String API_URL = "https://papago.apigw.ntruss.com/nmt/v1/translation";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String SOURCE_LANGUAGE = "ko";

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
        validateTranslateParam(koreanText, targetLanguageCode);

//        FormBody requestBody = buildRequestBody(koreanText, targetLanguageCode);
        RequestBody requestBody = buildRequestBody(koreanText, targetLanguageCode);
        Request request = buildRequest(requestBody);

        try (Response response = client.newCall(request).execute()) {
            return parseResponse(response);
        } catch (IOException e) {
            throw new RuntimeException("번역 API 통신 실패 : " + e.getMessage());
        }
    }

//    private static @NotNull FormBody buildRequestBody(String koreanText, String targetLanguageCode) {
//        FormBody requestBody = new FormBody.Builder()
//                .add("source", SOURCE_LANGUAGE)
//                .add("target", targetLanguageCode)
//                .add("text", koreanText)
//                .build();
//        return requestBody;
//    }

    //    TODO Json 방식
    private static RequestBody buildRequestBody(String koreanText, String targetLanguageCode) {
        JsonObject json = new JsonObject();
        json.addProperty("source", SOURCE_LANGUAGE);
        json.addProperty("target", targetLanguageCode);
        json.addProperty("text", koreanText);

        return RequestBody.create(json.toString(), JSON);
    }

//    private @NotNull Request buildRequest(FormBody requestBody) {
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .header("X-NCP-APIGW-API-KEY-ID", clientId)
//                .header("X-NCP-APIGW-API-KEY", clientSecret)
//                .post(requestBody)
//                .build();
//        return request;
//    }

    private Request buildRequest(RequestBody requestBody) {
        Request request = new Request.Builder()
                .url(API_URL)
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .post(requestBody)
                .build();
        return request;
    }

    private static TargetText parseResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            throw new RuntimeException("API 호출 실패 : " + response.code());
        }

        String responseBody = response.body().string();
        // JSON 파싱
        String translatedText = extractTranslateText(responseBody);

        return new TargetText(translatedText);
    }

    private static String extractTranslateText(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

        String translatedText = jsonObject
                .getAsJsonObject("message")
                .getAsJsonObject("result")
                .get("translatedText")
                .getAsString();
        return translatedText;
    }

    private void validate(String clientId, String clientSecret) {
        if (clientId == null || clientId.isEmpty()) {
            throw new IllegalArgumentException("Client ID가 설정되지 않았습니다.");
        }
        if (clientSecret == null || clientSecret.isEmpty()) {
            throw new IllegalArgumentException("Client Secret이 설정되지 않았습니다.");
        }
    }

    private void validateTranslateParam(String koreanText, String targetLanguageCode) {
        if (koreanText == null || koreanText.isEmpty()) {
            throw new IllegalArgumentException("번역할 텍스트가 비어있습니다.");
        }

        if (targetLanguageCode == null || targetLanguageCode.isEmpty()) {
            throw new IllegalArgumentException("목표 언어가 비어있습니다.");
        }
    }
}
