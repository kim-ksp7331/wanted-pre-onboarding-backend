package wanted.preonboarding.board.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import wanted.preonboarding.board.post.dto.PostDTO;
import wanted.preonboarding.board.post.service.PostService;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostService postService;
    @Test
    void postPost() throws Exception {
        // given
        String title = "title1";
        String content = "content1";
        PostDTO.Post dto = PostDTO.Post.builder().title(title).content(content).build();
        long id = 1L;
        String json = objectMapper.writeValueAsString(dto);

        BDDMockito.given(postService.createPost(Mockito.nullable(Authentication.class), Mockito.any(PostDTO.Post.class))).willReturn(id);
        String urlTemplate = "/posts";

        // when
        ResultActions actions = mockMvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        // then
        String resourceUrl = urlTemplate + "/" + id;
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("location", is(equalTo(resourceUrl))));
    }

    @Test
    void getPost() throws Exception {
        // given
        Long postId = 1L;
        String title = "title1";
        String content = "content1";
        String author = "asdf@gmail.com";
        PostDTO.Response response = PostDTO.Response.builder().author(author).postId(postId).title(title).content(content).build();

        BDDMockito.given(postService.findPost(1L)).willReturn(response);
        String urlTemplate = "/posts/{id}";

        // when
        ResultActions actions = mockMvc.perform(
                get(urlTemplate, postId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(postId))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    void getPosts() throws Exception {
        // given
        int page = 1;
        int size = 10;
        int totalPages = 1;
        int totalSize = 3;
        final String title = "title";
        final String content = "content";
        final String emailPrefix = "abc";

        ArrayList<PostDTO.Response> list = new ArrayList<>();
        for (long i = 1; i <= totalSize; i++) {
            long postId = i;
            String author = String.format("%s%d@gmail.com", emailPrefix, i);
            list.add(PostDTO.Response.builder()
                    .postId(postId).author(author).title(title + i).content(content + i).build());
        }
        PostDTO.Responses responses = PostDTO.Responses.builder().posts(list)
                .page(page).size(size).totalPages(totalPages).totalElements(totalSize).build();

        BDDMockito.given(postService.findPosts(Mockito.anyInt(), Mockito.anyInt())).willReturn(responses);
        String urlTemplate = "/posts";

        // when
        ResultActions actions = mockMvc.perform(
                get(urlTemplate)
                        .param("page", page + "")
                        .param("size", size + "")
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts[*].title").value(everyItem(is(startsWith(title)))))
                .andExpect(jsonPath("$.posts[*].content").value(everyItem(is(startsWith(content)))))
                .andExpect(jsonPath("$.posts[*].author").value(everyItem(is(startsWith(emailPrefix)))))
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.totalElements").value(totalSize))
                .andExpect(jsonPath("$.totalPages").value(totalPages));
    }
}