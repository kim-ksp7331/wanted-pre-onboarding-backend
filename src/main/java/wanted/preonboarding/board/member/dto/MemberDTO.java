package wanted.preonboarding.board.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@Builder
public class MemberDTO {
    @Pattern(regexp = ".*@.*")
    private String email;
    @Size(min = 8)
    private String password;

}
