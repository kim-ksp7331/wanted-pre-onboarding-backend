package wanted.preonboarding.board.Member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.preonboarding.board.Member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
