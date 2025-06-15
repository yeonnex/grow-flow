package me.seoyeon.api.exception;

public class ErrorResponse {
  private final String code;
  private final String message;

  public ErrorResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  private ErrorResponse(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  public static ErrorResponse of(ErrorCode errorCode) {
    return new ErrorResponse(errorCode);
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
