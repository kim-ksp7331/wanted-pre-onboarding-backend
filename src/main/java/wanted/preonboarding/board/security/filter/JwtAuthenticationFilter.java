package wanted.preonboarding.board.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wanted.preonboarding.board.exception.LoginValidationException;
import wanted.preonboarding.board.member.dto.MemberDTO;
import wanted.preonboarding.board.security.utils.ErrorResponder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            MemberDTO memberDTO = objectMapper.readValue(request.getInputStream(), MemberDTO.class);
            validateDTO(memberDTO, response);
            UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                    .unauthenticated(memberDTO.getEmail(), memberDTO.getPassword());
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateDTO(MemberDTO memberDTO, HttpServletResponse response) throws IOException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberDTO);
        if(!violations.isEmpty()) {
            throw new LoginValidationException("Email or Password Not Valid");
        }
    }
}
