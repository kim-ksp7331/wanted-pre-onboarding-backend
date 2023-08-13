package wanted.preonboarding.board.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.preonboarding.board.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

}
