package wanted.preonboarding.board.post.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wanted.preonboarding.board.member.entity.Member;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.entity.Post;

import java.util.stream.Collectors;

@Component
public class PostMapper {
    public Post postDTOToEntity(PostDTO.Post post, Long memberId) {
        return Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .member(Member.builder().id(memberId).build())
                .build();
    }

    public PostDTO.Response entityToResponseDTO(Post post) {
        return PostDTO.Response.builder()
                .postId(post.getId())
                .author(post.getMember().getEmail())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public PostDTO.Responses entityPageToResponsesDTO(Page<Post> posts) {
        int page = posts.getNumber() + 1;
        return PostDTO.Responses.builder()
                .posts(posts.getContent()
                        .stream().map(this::entityToResponseDTO).collect(Collectors.toList()))
                .page(page)
                .size(posts.getSize())
                .totalPages(posts.getTotalPages())
                .totalElements(posts.getTotalElements())
                .build();
    }
}
