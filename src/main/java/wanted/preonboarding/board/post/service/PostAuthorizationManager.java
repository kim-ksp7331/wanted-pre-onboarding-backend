package wanted.preonboarding.board.post.service;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import wanted.preonboarding.board.post.entity.Post;

import java.util.function.Supplier;

@Component
public class PostAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final PostService postService;

    public PostAuthorizationManager(PostService postService) {
        this.postService = postService;
    }
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Authentication auth = authentication.get();
        if(auth == null || !auth.getPrincipal().toString().chars().allMatch(Character::isDigit)) return new AuthorizationDecision(false);

        boolean hasUser = auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));

        String postId = object.getVariables().get("id");
        if(!postId.chars().allMatch(Character::isDigit)) return new AuthorizationDecision(false);

        Post post = postService.findVerifiedPost(Long.parseLong(postId));
        Long memberId = postService.getMemberIdFromAuthentication(auth);
        boolean isResourceOwner = post.getMember().getId().equals(memberId);

        return new AuthorizationDecision(hasUser && isResourceOwner);
    }
}
