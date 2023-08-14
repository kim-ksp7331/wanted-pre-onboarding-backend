package wanted.preonboarding.board.post.mapper;

import org.junit.jupiter.api.Test;
import wanted.preonboarding.board.member.entity.Member;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.entity.Post;

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
}