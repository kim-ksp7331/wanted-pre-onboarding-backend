package wanted.preonboarding.board.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@Builder
public class MemberDTO {
    @Email
    private String email;
    @Size(min = 8)
    private String password;

}
