package me.seoyeon.api.service;

import java.util.List;
import me.seoyeon.api.client.NotionClient;
import me.seoyeon.api.client.dto.DatabaseQueryResponse;
import me.seoyeon.api.dto.PageFilter;
import me.seoyeon.api.dto.NotionDatabasePageResponse;
import org.springframework.stereotype.Service;

@Service
public class NotionService {

  private final NotionClient notionClient;

  public NotionService(NotionClient notionClient) {
    this.notionClient = notionClient;
  }

  public NotionDatabasePageResponse queryDBPages(String databaseId, PageFilter request) {
    DatabaseQueryResponse response = notionClient.databases().query(databaseId, request);
    List<NotionDatabasePageResponse.NotionPage> pages =
        response.results().stream()
            .map(
                r ->
                    new NotionDatabasePageResponse.NotionPage(
                        r.properties().get("Name").title().get(0).plainText(), r.url()))
            .toList();
    return new NotionDatabasePageResponse(pages, response.hasMore(), response.nextCursor());
  }
}
