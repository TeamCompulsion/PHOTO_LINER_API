package kr.kro.photoliner.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.DateTimeException;
import java.util.UUID;
import kr.kro.photoliner.global.code.ApiResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handlePhotoUploadException(
            HttpServletRequest request,
            CustomException e
    ) {
        return buildErrorResponse(request, e.getErrorCode(), e.getFullMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
            HttpServletRequest request,
            IllegalArgumentException e
    ) {
        return buildErrorResponse(request, ApiResponseCode.ILLEGAL_ARGUMENT, e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(
            HttpServletRequest request,
            IllegalStateException e
    ) {
        return buildErrorResponse(request, ApiResponseCode.ILLEGAL_STATE, e.getMessage());
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<Object> handleDateTimeException(
            HttpServletRequest request,
            DateTimeException e
    ) {
        return buildErrorResponse(request, ApiResponseCode.INVALID_DATE_TIME, e.getMessage());
    }

    private ResponseEntity<Object> buildErrorResponse(
            HttpServletRequest request,
            ApiResponseCode errorCode,
            String errorMessage
    ) {
        String errorTraceId = UUID.randomUUID().toString();

        log.error(errorMessage);

        ErrorResponse response = new ErrorResponse(
                errorCode.getHttpStatus().value(),
                errorCode.getCode(),
                errorCode.getMessage(),
                errorTraceId
        );
        return ResponseEntity
                .status(response.status())
                .body(request);
    }
}
