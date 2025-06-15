package me.seoyeon.api.exception;

/** 모든 비즈니스 관련 예외가 상속받을 최상위 예외 클래스 */
public abstract class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
