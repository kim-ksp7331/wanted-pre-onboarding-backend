package wanted.preonboarding.board.Member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import wanted.preonboarding.board.Member.dto.MemberDTO;
import wanted.preonboarding.board.Member.service.MemberService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Validated
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Void> joinMember(@RequestBody @Valid MemberDTO memberDTO) {
        Long memberId = memberService.createMember(memberDTO);
        URI uri = UriComponentsBuilder.newInstance()
                .path("/members/{id}")
                .buildAndExpand(memberId)
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
