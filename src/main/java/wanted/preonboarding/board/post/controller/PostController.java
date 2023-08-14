package wanted.preonboarding.board.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.service.PostService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
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

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO.Response> getPost(@PathVariable("id") @Positive Long postId) {
        PostDTO.Response response = postService.findPost(postId);
        return ResponseEntity.ok(response);
    }
}
