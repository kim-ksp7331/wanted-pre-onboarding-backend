package wanted.preonboarding.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PostDTO {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post {
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
