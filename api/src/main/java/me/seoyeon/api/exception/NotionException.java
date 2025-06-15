package me.seoyeon.api.exception;

public class NotionException extends BusinessException {
  public NotionException(ErrorCode errorCode) {
    super(errorCode);
  }

  public static class InvalidPageIdException extends NotionException {
    public InvalidPageIdException(ErrorCode errorCode) {
      super(errorCode);
    }
  }
}
