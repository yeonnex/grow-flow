package me.seoyeon.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /** BusinessException을 상속받는 모든 커스텀 예외 처리 */
  @ExceptionHandler(BusinessException.class)
  ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
    log.error("BusinessException: {}", e.getMessage(), e);
    ErrorCode errorCode = e.getErrorCode();
    ErrorResponse response = ErrorResponse.of(errorCode);
    return new ResponseEntity<>(response, errorCode.getStatus());
  }

  /** 처리되지 않은 모든 예외를 잡아 500 에러로 처리 */
  @ExceptionHandler(Exception.class)
  ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("UnhandledException: {}", e.getMessage(), e);
    ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /** 쿼리파라미터, HTTP 요청헤더, 경로변수 등에 대한 유효성 검증 실패시 발생하는 예외 처리 */
  @ExceptionHandler(HandlerMethodValidationException.class)
  ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(
      HandlerMethodValidationException e) {
    log.error("HandlerMethodValidationException: {}", e.getMessage(), e);
    ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
    return new ResponseEntity<>(response, ErrorCode.INVALID_INPUT_VALUE.getStatus());
  }
}
