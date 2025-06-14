package me.seoyeon.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.util.Map;
import me.seoyeon.api.client.dto.DatabaseQueryResponse;
import me.seoyeon.api.converter.NotionFilterConverter;
import me.seoyeon.api.dto.PageFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseApi {

  private final Logger logger = LoggerFactory.getLogger(DatabaseApi.class);
  private final NotionHttpExecutor executor;
  private final ObjectMapper objectMapper;

  public DatabaseApi(NotionHttpExecutor executor) {
    this.executor = executor;
    this.objectMapper =
        new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public DatabaseQueryResponse query(String databaseId, PageFilter filter) {
    String path = "/databases/" + databaseId + "/query";
    // request -> Notion API 검색 요청 형식으로 변환 필요
    Map<String, Object> converted = NotionFilterConverter.convert(filter);
    String body;
    try {
      body = objectMapper.writeValueAsString(converted);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    NotionBasicRequest notionQuery = NotionBasicRequest.of("POST", path, body);
    String result = executor.execute(notionQuery);
    // Notion API 응답 그대로 파싱 (Raw)
    try {
      return objectMapper.readValue(result, DatabaseQueryResponse.class);
    } catch (JsonProcessingException e) {
      logger.error("Notion Database query API 응답 데이터 파싱 실패");
      throw new RuntimeException(e);
    }
  }
}