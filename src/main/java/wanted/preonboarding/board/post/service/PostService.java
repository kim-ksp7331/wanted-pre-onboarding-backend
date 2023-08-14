package wanted.preonboarding.board.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.preonboarding.board.member.service.MemberService;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.entity.Post;
import wanted.preonboarding.board.post.mapper.PostMapper;
import wanted.preonboarding.board.post.repository.PostRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper mapper;

    public Long createPost(Authentication authentication, PostDTO.Post dto) {
        Long memberId = getMemberIdFromAuthentication(authentication);
        Post post = mapper.postDTOToPost(dto, memberId);
        return postRepository.save(post).getId();
    }

    private Long getMemberIdFromAuthentication(Authentication authentication) {
        return Long.parseLong(authentication.getPrincipal().toString());
    }
}
