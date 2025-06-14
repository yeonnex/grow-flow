package me.seoyeon.api.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import me.seoyeon.api.exception.NotionApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultNotionHttpExecutor implements NotionHttpExecutor {

  private final HttpClient httpClient;
  private final Logger logger = LoggerFactory.getLogger(DefaultNotionHttpExecutor.class);

  @Value("${notion.api.base-url}")
  private String notionBaseUrl;

  @Value("${notion.api.auth-token}")
  private String authToken;

  @Value("${notion.api.version}")
  private String apiVersion;

  public DefaultNotionHttpExecutor() {
    this.httpClient = HttpClient.newHttpClient();
  }

  @Override
  public String execute(NotionBasicRequest request) {
    // 기본 요청 헤더 정보 세팅
    HttpRequest.Builder builder =
        HttpRequest.newBuilder()
            .uri(URI.create(notionBaseUrl + request.path()))
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .header("Notion-Version", apiVersion);

    // HTTP 메서드 세팅
    switch (request.method().toUpperCase()) {
      case "GET" -> builder.GET();
      case "POST" -> {
        if (request.body() != null) {
          logger.info("▶▶▶ Notion Query Request Body: {}", request.body());
          builder.POST(HttpRequest.BodyPublishers.ofString(request.body()));
        } else {
          builder.POST(HttpRequest.BodyPublishers.noBody());
        }
      }
      case "PATCH" -> builder.method("PATCH", HttpRequest.BodyPublishers.ofString(request.body()));
      case "DELETE" -> builder.DELETE();
      default -> throw new IllegalArgumentException("지원되지 않는 HTTP 메서드입니다.");
    }

    HttpRequest httpRequest = builder.build();

    try {
      HttpResponse<String> response =
          httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() >= 200 && response.statusCode() < 300) {
        return response.body();
      } else {
        String errorMessage =
            "Notion API 호출 정상 응답 실패: status = %d, body=%s"
                .formatted(response.statusCode(), response.body());
        throw new NotionApiException(errorMessage);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
