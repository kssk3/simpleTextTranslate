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
import woowacourse.textTranslate.error.ErrorMessage;

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

    @DisplayName("언어 번역 통과")
    @Test
    void 언어_번역_통과() {
        // given
        String jsonBody = """
                {"message":{
                    "result":{
                        "translatedText" : "こんにちは"
                        }
                    }
                }""";

        // when
        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonBody)
                .setHeader("Content-Type", "application/json"));

        //then
        TargetText japanese = service.translate("안녕하세요", TargetLanguage.JAPANESE.getCode());
        assertEquals("こんにちは", japanese.getTranslatedText());
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

    @DisplayName("responseBody가 null 일 때 예외 발생")
    @Test
    void responseBody가_null_일_때_예외_발생() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(""));

        // when & then
        assertThatThrownBy(() -> service.translate("안녕하세요", TargetLanguage.ENGLISH.getCode()))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("clientId 값이 null 일때 예외 발생")
    @Test
    void clientId_값이_null_일때_예외_발생() {
        assertThatThrownBy(() -> new PapagoTranslationService(null, "secret"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("clientSecret 값이 빈 문자열일 때 예외 발생")
    @Test
    void clientSecret_값이_빈_문자열일_때_예외_발생() {
        assertThatThrownBy(() -> new PapagoTranslationService("clientId", ""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("translator 호출시 KoreanText가 null이면 예외 발생")
    @Test
    void translator_호출시_KoreanText가_null이면_예외_발생() {
        assertThatThrownBy(() -> service.translate(null, TargetLanguage.ENGLISH.getCode()))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
}