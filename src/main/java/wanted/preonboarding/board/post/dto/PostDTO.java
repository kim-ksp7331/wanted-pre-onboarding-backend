package wanted.preonboarding.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class PostDTO {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post {
        private String title;
        private String content;
    }
}