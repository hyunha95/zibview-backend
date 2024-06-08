package com.view.zib.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.view.zib.domain.comment.controller.request.CreateCommentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostCommandController.class)
@WithMockUser(username = "user")
class PostCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // oauth2로 인증을 구현하다보니 @WithMockUser가 작동하지 않아서 .with(SecurityMockMvcRequestPostProcessors.jwt())로 대체
    @Test
    void shouldFailOnCreateCommentWhenRequestIsInvalid() throws Exception {
        // given
        CreateCommentRequest request = new CreateCommentRequest(null, null, null);

        // when
        // then
        mockMvc.perform(post("/api/posts/comments")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid input"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.invalidInputs[0].name").value("comment"))
                .andExpect(jsonPath("$.invalidInputs[1].name").value("parentCommentId"));
    }

}