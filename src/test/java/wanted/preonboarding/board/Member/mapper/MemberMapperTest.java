package wanted.preonboarding.board.Member.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import wanted.preonboarding.board.Member.dto.MemberDTO;
import wanted.preonboarding.board.Member.entity.Member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class MemberMapperTest {
    @InjectMocks
    private MemberMapper memberMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void DTOToEntity() {
        // given
        String email = "abcd@gmail.com";
        String password = "asdf1234";
        MemberDTO memberDTO = MemberDTO.builder().email(email).password(password).build();
        BDDMockito.given(passwordEncoder.encode(password)).willReturn(password);

        // when
        Member result = memberMapper.DTOToEntity(memberDTO);

        // then
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getPassword()).isEqualTo(password);
    }
}