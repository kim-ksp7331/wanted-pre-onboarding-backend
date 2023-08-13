package wanted.preonboarding.board.Member.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wanted.preonboarding.board.Member.dto.MemberDTO;
import wanted.preonboarding.board.Member.entity.Member;

@Component
@RequiredArgsConstructor
public class MemberMapper {
    private final PasswordEncoder passwordEncoder;
    public Member DTOToEntity(MemberDTO memberDTO) {
        return Member.builder()
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .build();
    }
}
