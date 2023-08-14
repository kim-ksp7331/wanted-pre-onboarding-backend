package wanted.preonboarding.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.preonboarding.board.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
