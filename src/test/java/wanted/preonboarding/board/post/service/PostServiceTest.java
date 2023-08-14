package wanted.preonboarding.board.post.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.entity.Post;
import wanted.preonboarding.board.post.mapper.PostMapper;
import wanted.preonboarding.board.post.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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

        BDDMockito.given(postMapper.postDTOToEntity(dto, memberId)).willReturn(post);
        BDDMockito.given(postRepository.save(post)).willReturn(save);

        // when
        Long result = postService.createPost(authentication, dto);

        // then
        assertThat(result).isEqualTo(postId);
    }

    @Test
    void findPost() {
        // given
        Long postId = 1L;
        String title = "title1";
        String content = "content1";
        String author = "asdf@gmail.com";
        Post post = Post.builder().id(postId).title(title).content(content).build();
        PostDTO.Response response = PostDTO.Response.builder().author(author).postId(postId).title(title).content(content).build();

        BDDMockito.given(postRepository.findById(1L)).willReturn(Optional.ofNullable(post));
        BDDMockito.given(postMapper.entityToResponseDTO(post)).willReturn(response);

        // when
        PostDTO.Response result = postService.findPost(postId);

        // then
        assertThat(result).isEqualTo(response);
    }

    @Test
    void findPosts() {
        // given
        int page = 1;
        int size = 10;
        int totalPages = 1;
        int totalSize = 2;
        PageImpl<Post> postPage = new PageImpl<>(List.of());

        ArrayList<PostDTO.Response> list = new ArrayList<>();
        for (long i = 0; i < totalSize; i++) {
            long postId = i;
            String author = String.format("abc%d@gmail.com", i);
            String title = "title";
            String content = "content";
            list.add(PostDTO.Response.builder()
                    .postId(postId).author(author).title(title + i).content(content + i).build());
        }
        PostDTO.Responses responses = PostDTO.Responses.builder().posts(list)
                .page(page).size(size).totalPages(totalPages).totalElements(totalSize).build();

        BDDMockito.given(postRepository.findAll(Mockito.any(Pageable.class))).willReturn(postPage);
        BDDMockito.given(postMapper.entityPageToResponsesDTO(postPage)).willReturn(responses);

        // when
        PostDTO.Responses result = postService.findPosts(page, size);

        // then
        assertThat(result).isEqualTo(responses);
    }
}