package ru.t1.HomeWork2;

import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import ru.t1.HomeWork2.interceptor.HttpLoggingInterceptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HomeWork2ApplicationTests {
    private HttpLoggingInterceptor httpLoggingInterceptor;
    private LogCaptor logCaptor;

    @BeforeEach
    public void setUp() {
        httpLoggingInterceptor = new HttpLoggingInterceptor();
        logCaptor = LogCaptor.forClass(HttpLoggingInterceptor.class);
    }

    @Test
    public void testPreHandle() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("GET");
        request.setRequestURI("/test");
        request.addHeader("User-Agent", "Postman");
        request.addHeader("Accept", "application/json");

        boolean result = httpLoggingInterceptor.preHandle(request, response, new Object());
        assertThat(result).isTrue();
        assertThat(request.getAttribute("startTime")).isNotNull();

        assertThat(logCaptor.getLogs())
                .hasSize(1)
                .anyMatch(log -> log.contains("Incoming request: method=GET, uri=/test, " +
                        "headers=[User-Agent:\"Postman\", Accept:\"application/json\"]"));
    }

    @Test
    public void testAfterCompletion() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("GET");
        request.setRequestURI("/test");
        request.addHeader("User-Agent", "Postman");
        request.addHeader("Accept", "application/json");
        response.addHeader("Date", "Mon, 11 Mar 2019 12:59:38 GMT");

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        httpLoggingInterceptor.afterCompletion(request, response, new Object(), null);

        long duration = System.currentTimeMillis() - startTime;

        List<String> logs = logCaptor.getLogs();
        assertThat(logs)
                .hasSize(1)
                .anyMatch(log -> log.contains("Outgoing response: status=200, headers=[Date:\"Mon, 11 Mar 2019 12:59:38 GMT\"], duration="));
    }
}