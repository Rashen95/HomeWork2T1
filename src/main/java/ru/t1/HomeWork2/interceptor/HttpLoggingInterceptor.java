package ru.t1.HomeWork2.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@Component
@Slf4j
public class HttpLoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        logRequest(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        logResponse(response, duration);
    }

    private void logRequest(HttpServletRequest request) {
        String logString = String.format("Incoming request: method=%s, uri=%s, headers=%s",
                request.getMethod(), request.getRequestURI(), getHeaders(request));
        log(logString);
    }

    private void logResponse(HttpServletResponse response, long duration) {
        String logString = String.format("Outgoing response: status=%s, headers=%s, duration=%sms",
                response.getStatus(), getHeaders(response), duration);
        log(logString);
    }

    private void log(String logString) {
        if (log.isDebugEnabled()) {
            log.debug(logString);
        } else if (log.isInfoEnabled()) {
            log.info(logString);
        } else if (log.isWarnEnabled()) {
            log.warn(logString);
        } else if (log.isErrorEnabled()) {
            log.error(logString);
        } else if (log.isTraceEnabled()) {
            log.trace(logString);
        }
    }

    private String getHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));
        }

        return headers.toString();
    }

    private String getHeaders(HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();

        for (String headerName : response.getHeaderNames()) {
            headers.add(headerName, response.getHeader(headerName));
        }

        return headers.toString();
    }
}