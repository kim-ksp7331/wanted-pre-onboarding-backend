package wanted.preonboarding.board.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

public class LoginValidationException extends InternalAuthenticationServiceException {
    public LoginValidationException(String message) {
        super(message);
    }
}
