package wanted.preonboarding.board.post.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.entity.Post;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {
    private PostMapper postMapper = new PostMapper();
    @Test
    void postDTOToPost() {
        // given
        String title = "title1";
        String content = "content1";
        Long memberId = 1L;
        PostDTO.Post dto = PostDTO.Post.builder().title(title).content(content).build();

        // when
        Post post = postMapper.postDTOToPost(dto, memberId);

        // then
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getMember().getId()).isEqualTo(memberId);
    }
}