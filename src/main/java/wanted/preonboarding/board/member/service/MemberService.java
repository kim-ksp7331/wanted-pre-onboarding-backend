package wanted.preonboarding.board.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.preonboarding.board.exception.BusinessLogicException;
import wanted.preonboarding.board.exception.ExceptionCode;
import wanted.preonboarding.board.member.dto.MemberDTO;
import wanted.preonboarding.board.member.entity.Member;
import wanted.preonboarding.board.member.mapper.MemberMapper;
import wanted.preonboarding.board.member.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper mapper;

    public Long createMember(MemberDTO memberDTO) {
        existsMember(memberDTO.getEmail());
        Member member = memberRepository.save(mapper.DTOToEntity(memberDTO));
        return member.getId();
    }

    private void existsMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
}
