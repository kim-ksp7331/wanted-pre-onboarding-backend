package wanted.preonboarding.board.post.entity;

import lombok.*;
import wanted.preonboarding.board.member.entity.Member;
import wanted.preonboarding.board.post.dto.PostDTO;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    public void update(PostDTO.Patch dto) {
        Optional.ofNullable(dto.getTitle()).ifPresent(title -> setTitle(title));
        Optional.ofNullable(dto.getContent()).ifPresent(content -> setContent(content));
    }
}
