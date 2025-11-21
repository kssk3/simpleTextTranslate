package woowacourse.textTranslate.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Optional;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import woowacourse.textTranslate.domain.TargetText;
import woowacourse.textTranslate.error.ErrorMessage;

public class PapagoTranslationService implements TranslationService {

    private static final String API_URL = "https://papago.apigw.ntruss.com/nmt/v1/translation";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String SOURCE_LANGUAGE = "ko";

    private final String apiUrl;
    private final OkHttpClient client;
    private final String clientId;
    private final String clientSecret;

    public PapagoTranslationService(String clientId, String clientSecret) {
        this(clientId, clientSecret, "https://papago.apigw.ntruss.com/nmt/v1/translation", new OkHttpClient());
    }

    public PapagoTranslationService(String clientId, String clientSecret, String apiUrl, OkHttpClient client) {
        validate(clientId, clientSecret);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.apiUrl = apiUrl;
        this.client = client;
    }

    private void validate(String clientId, String clientSecret) {
        if (clientId == null || clientId.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.NAVER_CLIENT_ID.getMessage());
        }
        if (clientSecret == null || clientSecret.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.NAVER_CLIENT_SECRET.getMessage());
        }
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

    private void validateTranslateParam(String koreanText, String targetLanguageCode) {
        if (koreanText == null || koreanText.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_TEXT_INPUT.getMessage());
        }

        if (targetLanguageCode == null || targetLanguageCode.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_CHOICE_LANGUAGE.getMessage());
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

    protected static RequestBody buildRequestBody(String koreanText, String targetLanguageCode) {
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

    protected Request buildRequest(RequestBody requestBody) {
        Request request = new Request.Builder()
                .url(API_URL)
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .post(requestBody)
                .build();
        return request;
    }

    protected static TargetText parseResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            throw new RuntimeException("API 호출 실패 : " + response.code());
        }

        ResponseBody body = response.body();
        if (body == null) {
            throw new IllegalArgumentException(ErrorMessage.RESPONSE_BODY_EMPTY.getMessage());
        }
        // JSON 파싱
        String translatedText = extractTranslateText(body.string());

        return new TargetText(translatedText);
    }

    private static String extractTranslateText(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

        return Optional.ofNullable(jsonObject.getAsJsonObject("message"))
                .map(message -> message.getAsJsonObject("result"))
                .map(result -> result.get("translatedText"))
                .map(element -> element.getAsString())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.RESPONSE_BODY_EMPTY.getMessage()));
    }
}
