package woowacourse.textTranslate.config;

import okhttp3.OkHttpClient;

public class HttpClientProvider {
    private HttpClientProvider() {
    }

    private static final OkHttpClient INSTANCE = new OkHttpClient();

    public static OkHttpClient getInstance() {
        return INSTANCE;
    }
}
