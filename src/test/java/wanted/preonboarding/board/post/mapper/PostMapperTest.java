package wanted.preonboarding.board.post.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import wanted.preonboarding.board.member.entity.Member;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.entity.Post;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

class PostMapperTest {
    private PostMapper postMapper = new PostMapper();
    @Test
    void postDTOToEntity() {
        // given
        String title = "title1";
        String content = "content1";
        Long memberId = 1L;
        PostDTO.Post dto = PostDTO.Post.builder().title(title).content(content).build();

        // when
        Post post = postMapper.postDTOToEntity(dto, memberId);

        // then
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getMember().getId()).isEqualTo(memberId);
    }

    @Test
    void entityToResponseDTO() {
        // given
        Long memberId = 2L;
        String email = "asdf@gmail.com";
        Long postId = 1L;
        String title = "title1";
        String content = "content1";
        Member member = Member.builder().id(memberId).email(email).build();
        Post post = Post.builder().id(postId).title(title).content(content).member(member).build();

        // when
        PostDTO.Response result = postMapper.entityToResponseDTO(post);

        // then
        assertThat(result.getPostId()).isEqualTo(postId);
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getAuthor()).isEqualTo(email);
    }

    @Test
    void entityPageToResponsesDTO() {
        // given
        ArrayList<Post> list = new ArrayList<>();
        final String title = "title";
        final String content = "content";
        final String emailPrefix = "abc";
        long totalSize = 3;
        for (long i = 1; i <= totalSize; i++) {
            long postId = i;
            String email = String.format("%s%d@gmail.com", emailPrefix, i);
            Member member = Member.builder().email(email).build();
            Post post = Post.builder().id(postId).title(title + i).content(content + i).member(member).build();
            list.add(post);
        }
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<Post> posts = new PageImpl<>(list, pageable, totalSize);

        // when
        PostDTO.Responses responses = postMapper.entityPageToResponsesDTO(posts);

        // then
        assertThat(responses.getPosts()).allSatisfy(response -> {
            assertThat(response.getTitle()).startsWith(title);
            assertThat(response.getContent()).startsWith(content);
            assertThat(response.getAuthor()).startsWith(emailPrefix);
        });
        assertThat(responses.getPage()).isEqualTo(1L);
    }
}