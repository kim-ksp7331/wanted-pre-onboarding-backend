package wanted.preonboarding.board.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.service.PostService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Validated
public class PostController {
    private final static String POST_URL = "/posts/{id}";

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> postPost(Authentication authentication, @RequestBody @Valid PostDTO.Post post) {
        Long id = postService.createPost(authentication, post);
        URI uri = UriComponentsBuilder.newInstance()
                .path(POST_URL)
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
