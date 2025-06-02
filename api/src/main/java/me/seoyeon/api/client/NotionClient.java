package me.seoyeon.api.client;

import org.springframework.stereotype.Component;

@Component
public class NotionClient {

  private final DatabaseApi databases;
  private final NotionHttpExecutor notionHttpExecutor;

  public NotionClient(NotionHttpExecutor notionHttpExecutor) {
    this.notionHttpExecutor = notionHttpExecutor;
    this.databases = new DatabaseApi(notionHttpExecutor);
  }

  public DatabaseApi databases() {
    return databases;
  }
}
