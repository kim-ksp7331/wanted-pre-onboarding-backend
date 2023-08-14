package wanted.preonboarding.board.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import wanted.preonboarding.board.member.dto.MemberDTO;
import wanted.preonboarding.board.member.service.MemberService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final static String MEMBER_URL = "/members/{id}";
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Void> joinMember(@RequestBody @Valid MemberDTO memberDTO) {
        Long memberId = memberService.createMember(memberDTO);
        URI uri = UriComponentsBuilder.newInstance()
                .path(MEMBER_URL)
                .buildAndExpand(memberId)
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
