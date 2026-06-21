package kr.ktb.finn_week6.global.customException;

public class IllegalResourceStateException extends RuntimeException {
    public IllegalResourceStateException(String message) {
        super(message);
    }
}
