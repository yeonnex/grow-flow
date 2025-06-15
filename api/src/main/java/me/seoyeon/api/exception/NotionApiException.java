package me.seoyeon.api.exception;

public class NotionApiException extends BusinessException {
  public NotionApiException(ErrorCode errorCode) {
    super(errorCode);
  }
}
