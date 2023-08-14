package wanted.preonboarding.board.response;

import lombok.Getter;
import wanted.preonboarding.board.exception.ExceptionCode;

@Getter
public class ErrorResponse {
    private int status;
    private String message;

    private ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, message);
    }
    public static ErrorResponse of(ExceptionCode exceptionCode) {
        return new ErrorResponse(exceptionCode.getStatus(), exceptionCode.getMessage());
    }

}
