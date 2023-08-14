package wanted.preonboarding.board.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public PostDTO.Response updatePost(PostDTO.Patch dto) {
        Post post = findVerifiedPost(dto.getPostId());
        post.update(dto);
        return mapper.entityToResponseDTO(post);
    }

    public PostDTO.Response findPost(Long postId) {
        return mapper.entityToResponseDTO(findVerifiedPost(postId));
    }

    public PostDTO.Responses findPosts(int page, int size) {
        int pageFromZero = page - 1;
        String defaultProperty = "id";
        Pageable pageable = PageRequest.of(pageFromZero, size, Sort.by(Sort.Direction.DESC, defaultProperty));
        Page<Post> posts = postRepository.findAll(pageable);
        return mapper.entityPageToResponsesDTO(posts);
    }
    public void deletePost(Long postId) {
        Post post = findVerifiedPost(postId);
        postRepository.delete(post);
    }

    Post findVerifiedPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }

    Long getMemberIdFromAuthentication(Authentication authentication) {
        return Long.parseLong(authentication.getPrincipal().toString());
    }
}
