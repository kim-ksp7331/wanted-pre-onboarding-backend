package wanted.preonboarding.board.post.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.entity.Post;
import wanted.preonboarding.board.post.mapper.PostMapper;
import wanted.preonboarding.board.post.repository.PostRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    @Test
    void createPost() {
        // given
        Long memberId = 1L;
        String title = "title1";
        String content = "content1";
        long postId = 1L;

        Authentication authentication = new TestingAuthenticationToken(memberId, null);
        PostDTO.Post dto = PostDTO.Post.builder().title(title).content(content).build();
        Post post = Post.builder().title(title).content(content).build();
        Post save = Post.builder().id(postId).title(title).content(content).build();

        BDDMockito.given(postMapper.postDTOToPost(dto, memberId)).willReturn(post);
        BDDMockito.given(postRepository.save(post)).willReturn(save);

        // when
        Long result = postService.createPost(authentication, dto);

        // then
        assertThat(result).isEqualTo(postId);
    }
}