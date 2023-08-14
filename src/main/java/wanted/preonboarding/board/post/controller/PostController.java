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

    @PatchMapping("/{id}")
    public ResponseEntity<PostDTO.Response> patchPost(@PathVariable("id") @Positive Long postId,
                                                      @RequestBody @Valid PostDTO.Patch patch) {
        patch.setPostId(postId);
        PostDTO.Response response = postService.updatePost(patch);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO.Response> getPost(@PathVariable("id") @Positive Long postId) {
        PostDTO.Response response = postService.findPost(postId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<PostDTO.Responses> getPosts(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        PostDTO.Responses responses = postService.findPosts(page, size);
        return ResponseEntity.ok(responses);
    }
}
