package wanted.preonboarding.board.post.mapper;

import org.springframework.stereotype.Component;
import wanted.preonboarding.board.member.entity.Member;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.entity.Post;

@Component
public class PostMapper {
    public Post postDTOToPost(PostDTO.Post post, Long memberId) {
        return Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .member(Member.builder().id(memberId).build())
                .build();
    }
}
