package me.seoyeon.api.exception;

public class NotionApiException extends RuntimeException {
  public NotionApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotionApiException(String message) {
    super(message);
  }
}
