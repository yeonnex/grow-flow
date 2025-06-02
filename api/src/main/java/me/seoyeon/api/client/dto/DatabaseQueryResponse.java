package me.seoyeon.api.client.dto;

import java.util.List;
import java.util.Map;

public record DatabaseQueryResponse(List<Page> results, boolean hasMore, String nextCursor) {
  public record Page(
      String id,
      String name,
      String url,
      String createdTime,
      String lastEditedTime,
      Map<String, NotionProperty> properties) {

    public record NotionProperty(String id, String type, SelectProperty select, List<TitleText> title) {}

    public record SelectProperty(String id, String name, String color) {}

    public record TitleText(String type, String plainText, String href) {}
  }
}
