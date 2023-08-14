package wanted.preonboarding.board.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@Builder
public class MemberDTO {
    @Pattern(regexp = ".*@.*")
    @NotNull
    private String email;
    @Size(min = 8)
    @NotNull
    private String password;

}
