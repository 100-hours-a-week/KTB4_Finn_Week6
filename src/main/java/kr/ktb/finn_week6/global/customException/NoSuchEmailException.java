package kr.ktb.finn_week6.global.customException;

public class NoSuchEmailException extends RuntimeException {
    public NoSuchEmailException(String message) {
        super(message);
    }
}
