// LoggingNotionHttpExecutor.java
package me.seoyeon.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary // NotionHttpExecutor 타입으로 주입 시, 이 클래스를 우선적으로 사용하도록 설정
public class LoggingNotionHttpExecutor implements NotionHttpExecutor {

    private final NotionHttpExecutor delegate; // 원본 Executor
    private final ObjectMapper objectMapper; // JSON 예쁘게 출력하기 위함
    private final Logger logger = LoggerFactory.getLogger(LoggingNotionHttpExecutor.class);

    // 생성자를 통해 원본(DefaultNotionHttpExecutor)을 주입받습니다.
    public LoggingNotionHttpExecutor(DefaultNotionHttpExecutor delegate, ObjectMapper objectMapper) {
        this.delegate = delegate;
        this.objectMapper = objectMapper;
    }

    @Override
    public String execute(NotionBasicRequest request) {
        long startTime = System.currentTimeMillis();
        
        // --- 요청 로그 출력 ---
        logRequest(request);

        try {
            // 원본 Executor에 작업 위임
            String responseBody = delegate.execute(request);
            long endTime = System.currentTimeMillis();

            // --- 응답 로그 출력 ---
            logResponse(request, responseBody, endTime - startTime);

            return responseBody;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            logger.error("<--- HTTP FAILED ({}ms): {} - {}", 
                         endTime - startTime, e.getClass().getSimpleName(), e.getMessage());
            throw e; // 예외는 그대로 다시 던짐
        }
    }

    private void logRequest(NotionBasicRequest request) {
        logger.info("---> method: {} path: {}", request.method(), request.path());
        if (request.body() != null) {
            logger.info("{}", prettyJson(request.body()));
        }
        logger.info("---> END HTTP");
    }

    private void logResponse(NotionBasicRequest request, String responseBody, long durationMs) {
        logger.info("<--- {} ({}ms)", request.path(), durationMs);
        if (responseBody != null && !responseBody.isEmpty()) {
            logger.info("{}", prettyJson(responseBody));
        }
        logger.info("<--- END HTTP");
    }

    // JSON 문자열을 예쁘게 포맷팅하는 헬퍼 메서드
    private String prettyJson(String json) {
        try {
            Object jsonObject = objectMapper.readValue(json, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            return json; // JSON 파싱 실패 시 원본 문자열 반환
        }
    }
}