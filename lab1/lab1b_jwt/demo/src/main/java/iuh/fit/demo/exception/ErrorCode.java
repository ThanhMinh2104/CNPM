package iuh.fit.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOTEXISTED(1000, "User Not Found");

    private final int code;
    private final String message;
}
