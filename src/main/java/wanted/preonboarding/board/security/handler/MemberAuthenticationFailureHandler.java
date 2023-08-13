package wanted.preonboarding.board.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import wanted.preonboarding.board.exception.LoginValidationException;
import wanted.preonboarding.board.security.utils.ErrorResponder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MemberAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof LoginValidationException) {
            ErrorResponder.sendErrorResponse(response, HttpStatus.BAD_REQUEST, exception.getMessage());
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
