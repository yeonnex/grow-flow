package me.seoyeon.api.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
  // Common
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C_001", "서버 내부 오류가 발생했습니다."),
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C_002", "유효하지 않은 입력 값입니다."),

  // Notion
  NOTION_PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "N_001", "존재하지 않는 노션 페이지입니다."),
  INVALID_NOTION_PAGE_ID(HttpStatus.BAD_REQUEST, "N_003", "노션 페이지 ID 형식이 올바르지 않습니다."),
  NOTION_API_CALL_FAILED(HttpStatus.BAD_REQUEST, "N_004", "노션 API 호출에 실패했습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
