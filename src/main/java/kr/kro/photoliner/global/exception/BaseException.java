package kr.kro.photoliner.global.exception;

public abstract class BaseException extends RuntimeException {
    protected final String detail;

    protected BaseException(String message) {
        super(message);
        this.detail = null;
    }

    protected BaseException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public String getFullMessage() {
        return String.format("%s %s", getMessage(), detail);
    }
}
