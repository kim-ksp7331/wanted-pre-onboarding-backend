package wanted.preonboarding.board.Member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wanted.preonboarding.board.Member.dto.MemberDTO;
import wanted.preonboarding.board.Member.service.MemberService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberService memberService;
    @Test
    void joinMember() throws Exception {
        // given
        String email = "abcd@gmail.com";
        String password = "asdf1234";
        long id = 1L;
        MemberDTO memberDTO = MemberDTO.builder().email(email).password(password).build();
        String content = objectMapper.writeValueAsString(memberDTO);
        String urlTemplate = "/members";

        BDDMockito.given(memberService.createMember(Mockito.any(MemberDTO.class))).willReturn(id);

        // when
        ResultActions actions = mockMvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        String resourceUrl = urlTemplate + "/" + id;
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("location", is(equalTo(resourceUrl))));

    }
}