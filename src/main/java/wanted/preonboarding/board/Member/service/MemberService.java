package wanted.preonboarding.board.Member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.preonboarding.board.Member.dto.MemberDTO;
import wanted.preonboarding.board.Member.entity.Member;
import wanted.preonboarding.board.Member.mapper.MemberMapper;
import wanted.preonboarding.board.Member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper mapper;

    public Long createMember(MemberDTO memberDTO) {
        Member member = memberRepository.save(mapper.DTOToEntity(memberDTO));
        return member.getId();
    }
}
