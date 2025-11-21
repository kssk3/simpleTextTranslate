package woowacourse.textTranslate.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.textTranslate.domain.TargetLanguage;
import woowacourse.textTranslate.domain.TargetText;

class PapagoTranslationServiceTest {

    private MockWebServer mockWebServer;
    private PapagoTranslationService service;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        service = new PapagoTranslationService(
                "test-id",
                "test-secret",
                mockWebServer.url("/").toString(),
                new OkHttpClient()
        );
    }

    @AfterEach
    void shutdown() throws IOException {
        mockWebServer.shutdown();
    }

    @DisplayName("정상 응답 파싱 통과")
    @Test
    void 정상_응답_파싱_통과() throws InterruptedException {
        // given
        String responseJson = """
                {
                    "message":{
                        "result":{
                            "translatedText": "Hello"
                        }
                    }
                }""";

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
                .setHeader("Content-Type", "application/json")
        );

        // when
        TargetText result = service.translate("안녕하세요", TargetLanguage.ENGLISH.getCode());
        RecordedRequest request = mockWebServer.takeRequest();

        // then
        assertEquals("Hello", result.getTranslatedText());
        assertEquals("POST", request.getMethod());
        assertEquals("test-id", request.getHeader("X-NCP-APIGW-API-KEY-ID"));
    }

    @DisplayName("API 호출 실패시 예외 발생")
    @Test
    void API_호출_실패시_예외_발생() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error")
        );

        assertThatThrownBy(() -> service.translate("안녕하세요", TargetLanguage.ENGLISH.getCode()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("API 호출 실패");
    }

    @DisplayName("잘못된 Json 형식 예외 발생")
    @Test
    void 잘못된_Json_형식_예외_발생() {
        // given
        String requestJson = "{\"Invalid\" : \"json\"}";

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(requestJson)
        );

        // when

        assertThatThrownBy(() -> service.translate("안녕하세요", TargetLanguage.ENGLISH.getCode()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("API");
    }
}