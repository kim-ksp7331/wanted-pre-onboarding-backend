package wanted.preonboarding.board.member.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import wanted.preonboarding.board.member.dto.MemberDTO;
import wanted.preonboarding.board.member.entity.Member;
import wanted.preonboarding.board.security.utils.CustomAuthorityUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberMapperTest {
    @InjectMocks
    private MemberMapper memberMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CustomAuthorityUtils authorityUtils;

    @Test
    void DTOToEntity() {
        // given
        String email = "abcd@gmail.com";
        String password = "asdf1234";
        MemberDTO memberDTO = MemberDTO.builder().email(email).password(password).build();
        BDDMockito.given(passwordEncoder.encode(password)).willReturn(password);
        BDDMockito.given(authorityUtils.getRoles()).willReturn(List.of());

        // when
        Member result = memberMapper.DTOToEntity(memberDTO);

        // then
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getPassword()).isEqualTo(password);
    }
}