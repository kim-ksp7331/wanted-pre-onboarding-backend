package wanted.preonboarding.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PostDTO {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post {
        @NotNull
        private String title;
        @NotNull
        private String content;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Patch {
        @Setter
        private Long postId;
        private String title;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long postId;
        private String author;
        private String title;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Responses {
        List<Response> posts;
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
    }
}
