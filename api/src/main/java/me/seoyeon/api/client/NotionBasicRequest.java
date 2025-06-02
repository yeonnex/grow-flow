package me.seoyeon.api.client;

public record NotionBasicRequest(String method, String path, String body) {
  public static NotionBasicRequest of(String method, String path, String body) {
    return new NotionBasicRequest(method, path, body);
  }
}
