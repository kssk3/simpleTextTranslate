package woowacourse.textTranslate.plugin;

import okhttp3.OkHttpClient;

public class KakaoTranslateService {

    private static final String API_URL = "https://kapi.kakao.com/v1/translation/translate";
    private final OkHttpClient client;
    private String apiKey;

    public KakaoTranslateService(String apiKey) {
        this.client = new OkHttpClient();
        validate(apiKey);
        this.apiKey = apiKey;
    }

    public String translateKoreanToEnglish(String koreanText) {
        return null;
    }

    private void validate(String apiKet) {
        if(apiKet == null || apiKet.isEmpty()) {
            throw new IllegalArgumentException("API키가 설정 되지 않았습니다.");
        }
    }

}
