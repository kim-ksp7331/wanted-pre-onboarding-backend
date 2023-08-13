package wanted.preonboarding.board.security.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import wanted.preonboarding.board.member.entity.Member;
import wanted.preonboarding.board.security.jwt.JWTService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JWTService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Member member = (Member) authentication.getPrincipal();

        String accessToken = delegateAccessToken(member);
        response.setHeader("Authorization", "Bearer " + accessToken);
    }
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getId().toString();
        Date expiration = jwtService.getTokenExpiration(jwtService.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtService.encodeBase64SecretKey(jwtService.getSecretKey());

        return jwtService.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }
}
