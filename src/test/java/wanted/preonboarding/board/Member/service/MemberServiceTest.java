package wanted.preonboarding.board.Member.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.preonboarding.board.Member.dto.MemberDTO;
import wanted.preonboarding.board.Member.entity.Member;
import wanted.preonboarding.board.Member.mapper.MemberMapper;
import wanted.preonboarding.board.Member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        BDDMockito.given(memberRepository.save(member)).willReturn(save);

        // when
        Long result = memberService.createMember(memberDTO);

        // then
        assertThat(result).isEqualTo(id);
    }
}