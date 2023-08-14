package wanted.preonboarding.board.member.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.preonboarding.board.member.dto.MemberDTO;
import wanted.preonboarding.board.member.entity.Member;
import wanted.preonboarding.board.member.mapper.MemberMapper;
import wanted.preonboarding.board.member.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberMapper mapper;
    @Test
    void createMember() {
        // given
        String email = "abcd@gmail.com";
        String password = "asdf1234";
        long id = 1L;

        MemberDTO memberDTO = MemberDTO.builder().email(email).password(password).build();
        Member member = Member.builder().email(email).password(password).build();
        Member save = Member.builder().id(id).email(email).password(password).build();

        BDDMockito.given(mapper.DTOToEntity(memberDTO)).willReturn(member);
        BDDMockito.given(memberRepository.findByEmail(email)).willReturn(Optional.empty());
        BDDMockito.given(memberRepository.save(member)).willReturn(save);

        // when
        Long result = memberService.createMember(memberDTO);

        // then
        assertThat(result).isEqualTo(id);
    }
}