package wanted.preonboarding.board.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.preonboarding.board.exception.BusinessLogicException;
import wanted.preonboarding.board.exception.ExceptionCode;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.entity.Post;
import wanted.preonboarding.board.post.mapper.PostMapper;
import wanted.preonboarding.board.post.repository.PostRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper mapper;

    public Long createPost(Authentication authentication, PostDTO.Post dto) {
        Long memberId = getMemberIdFromAuthentication(authentication);
        Post post = mapper.postDTOToEntity(dto, memberId);
        return postRepository.save(post).getId();
    }

    public PostDTO.Response findPost(Long postId) {
        return mapper.entityToResponseDTO(findVerifiedPost(postId));
    }

    private Post findVerifiedPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }

    private Long getMemberIdFromAuthentication(Authentication authentication) {
        return Long.parseLong(authentication.getPrincipal().toString());
    }
}
