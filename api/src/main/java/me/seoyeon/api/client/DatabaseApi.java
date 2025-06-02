package me.seoyeon.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import me.seoyeon.api.dto.NotionDBPageSearchRequest;
import me.seoyeon.api.client.dto.DatabaseQueryResponse;
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

  public DatabaseQueryResponse query(String databaseId, NotionDBPageSearchRequest request) {
    String path = "/databases/" + databaseId + "/query";
    // TODO request -> Notion API 검색 요청 형식으로 변환 필요
    NotionBasicRequest notionQuery = NotionBasicRequest.of("POST", path, null);
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
//https://www.notion.so/204c3be34a4680a2afd4ea966fdfbf83?v=204c3be34a4680e59f09000c2c1c5c61&source=copy_link
//https://www.notion.so/1fdc3be34a468055b88ce93ed3c8b85e?v=1fdc3be34a468028be63000c58036ca4&source=copy_link