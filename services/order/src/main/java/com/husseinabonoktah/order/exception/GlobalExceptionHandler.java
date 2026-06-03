package com.husseinabonoktah.order.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ExternalServiceUnavailableException.class)
  public ResponseEntity<ApiErrorResponse> handleExternalServiceUnavailable(
      ExternalServiceUnavailableException exception,
      HttpServletRequest request
  ) {
    return buildResponse(HttpStatus.SERVICE_UNAVAILABLE, exception, request);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiErrorResponse> handleBusinessException(
      BusinessException exception,
      HttpServletRequest request
  ) {
    return buildResponse(HttpStatus.BAD_REQUEST, exception, request);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleEntityNotFound(
      EntityNotFoundException exception,
      HttpServletRequest request
  ) {
    return buildResponse(HttpStatus.NOT_FOUND, exception, request);
  }

  private ResponseEntity<ApiErrorResponse> buildResponse(
      HttpStatus status,
      RuntimeException exception,
      HttpServletRequest request
  ) {
    return ResponseEntity.status(status).body(
        new ApiErrorResponse(
            Instant.now(),
            status.value(),
            status.getReasonPhrase(),
            exception.getMessage(),
            request.getRequestURI()
        )
    );
  }
}
