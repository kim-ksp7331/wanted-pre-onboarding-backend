package wanted.preonboarding.board.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import wanted.preonboarding.board.post.entity.Post;
import wanted.preonboarding.board.post.service.PostService;

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
}