package kr.kro.photoliner.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode {

    /**
     * 2xx Success (성공)
     */
    OK(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "요청이 성공적으로 처리되어 리소스가 생성되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "요청이 성공적으로 처리되었으나 반환할 내용이 없습니다."),

    /**
     * 400 Bad Request (잘못된 요청)
     */
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "잘못된 인자가 전달되었습니다."),
    ILLEGAL_STATE(HttpStatus.BAD_REQUEST, "잘못된 상태로 요청이 들어왔습니다."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "잘못된 입력값이 포함되어 있습니다."),
    INVALID_DATE_TIME(HttpStatus.BAD_REQUEST, "잘못된 날짜 형식입니다."),
    INVALID_FILE(HttpStatus.BAD_REQUEST, "유효하지 않은 파일입니다."),
    INVALID_FILE_NAME(HttpStatus.BAD_REQUEST, "유효하지 않은 파일명입니다."),

    /**
     * 401 Unauthorized (인증 필요)
     */
    WITHDRAWN_USER(HttpStatus.UNAUTHORIZED, "탈퇴한 계정입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 형식입니다."),

    /**
     * 403 Forbidden (인가 필요)
     */
    FORBIDDEN_USER_TYPE(HttpStatus.FORBIDDEN, "인가되지 않은 유저 타입입니다."),

    /**
     * 404 Not Found (리소스를 찾을 수 없음)
     */
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."),
    NOT_FOUND_PHOTO(HttpStatus.NOT_FOUND, "사진이 존재하지 않습니다."),
    NOT_FOUND_ALBUM(HttpStatus.NOT_FOUND, "앨범이 존재하지 않습니다."),

    /**
     * 409 CONFLICT (중복 혹은 충돌)
     */
    DUPLICATE_LOGIN_ID(HttpStatus.CONFLICT, "이미 존재하는 로그인 아이디입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    DUPLICATE_PHONE_NUMBER(HttpStatus.CONFLICT, "이미 존재하는 전화번호입니다."),

    /**
     * 429 Too Many Requests (요청량 초과)
     */
    TOO_MANY_REQUESTS_VERIFICATION(HttpStatus.TOO_MANY_REQUESTS, "하루 인증 횟수를 초과했습니다."),

    /**
     * 500 Internal Server Error (서버 오류)
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다."),
    FILE_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 처리 중 오류가 발생했습니다."),
    FILE_STORE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장 중 오류가 발생했습니다."),
    FILE_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 생성 중 오류가 발생했습니다."),
    DIRECTORY_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "폴더 생성 중 오류가 발생했습니다."),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제 중 오류가 발생했습니다."),
    TOKEN_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 파싱 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public String getCode() {
        return this.name();
    }
}
