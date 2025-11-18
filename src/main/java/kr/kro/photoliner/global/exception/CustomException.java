package kr.kro.photoliner.global.exception;

import kr.kro.photoliner.global.code.ApiResponseCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class CustomException extends RuntimeException {
    private final ApiResponseCode errorCode;
    private final String detail;

    private CustomException(ApiResponseCode errorCode, String detail) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public static CustomException of(ApiResponseCode errorCode) {
        return new CustomException(errorCode, "");
    }

    public static CustomException of(ApiResponseCode errorCode, String detail) {
        return new CustomException(errorCode, detail);
    }

    public String getFullMessage() {
        if (!StringUtils.hasText(detail)) {
            return super.getMessage();
        }
        return String.format("%s: %s", getMessage(), detail);

    }
}
