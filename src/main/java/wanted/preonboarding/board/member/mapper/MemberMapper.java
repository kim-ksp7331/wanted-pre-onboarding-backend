package wanted.preonboarding.board.member.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wanted.preonboarding.board.member.dto.MemberDTO;
import wanted.preonboarding.board.member.entity.Member;
import wanted.preonboarding.board.security.utils.CustomAuthorityUtils;

@Component
@RequiredArgsConstructor
public class MemberMapper {
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    public Member DTOToEntity(MemberDTO memberDTO) {
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .roles(authorityUtils.getRoles())
                .build();
        return member;
    }
}
