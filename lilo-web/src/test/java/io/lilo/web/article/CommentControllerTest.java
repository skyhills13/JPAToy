package io.lilo.web.article;

import io.lilo.config.DBConfig;
import io.lilo.domain.Comment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import io.lilo.service.CommentService;
import io.lilo.web.MvcTestUtil;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class CommentControllerTest {


    @Mock
    CommentService commentService;

    @InjectMocks
    CommentController commentController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MvcTestUtil.getMockMvc(commentController);
    }

    //TODO Refactoring (Ajax Process)
    @Test
    public void create() throws Exception {

        // - GIVEN
        Long articleId = 1L;
        String content = "testContent";
        Comment comment = new Comment(articleId, content);

        when(commentService.create(comment)).thenReturn(comment);

        // - WHEN, THEN
        mockMvc.perform(post("/article/comment")
                .param("articleId", articleId.toString())
                .param("content", content)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article/" + articleId));
    }

    //TODO Create. (current version is useless)
    @Test
    public void createWithInvalidCommentData() throws Exception {
        //fail("TODO");
    }

}
