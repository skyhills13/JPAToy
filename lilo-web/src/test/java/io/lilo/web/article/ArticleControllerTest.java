package io.lilo.web.article;

import io.lilo.config.DBConfig;
import io.lilo.config.WebConfig;
import io.lilo.domain.Article;
import io.lilo.domain.User;
import io.lilo.exception.ArticleCreationException;
import io.lilo.exception.ArticleModificationException;
import io.lilo.exception.ArticleNotFoundException;
import io.lilo.service.ArticleService;
import io.lilo.web.MvcTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class ArticleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    @Before
    public void setUp() {
        this.mockMvc = MvcTestUtil.getMockMvc(articleController);
    }

    @Test
    public void creationViewRequest() throws Exception {

        String expectedUrl = "/article/register";

        mockMvc.perform(get("/article/register"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX));
    }

    @Test
    public void createNewArticle() throws Exception {
        //TODO 첨부파일 업로드
        String title = "TITLE";
        String content = "CONTENT";

        mockMvc.perform(post("/article")
                        .param("title", title)
                        .param("content", content)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article/list"));
    }

    @Test
    public void createNewArticleWithWrongData() throws Exception {
        String title = "";
        String content = "";

        String expectedUrl = "/article/register";

        Mockito.doThrow(new ArticleCreationException(ArticleService.VALIDATION_EXCEPTION_MESSAGE)).when(articleService).create(any(Article.class));

        MvcResult mvcResult = mockMvc.perform(post("/article")
                        .param("title", title)
                        .param("content", content)
        )
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().size(2)).andReturn();

        String errorMessage = (String) mvcResult.getModelAndView().getModel().get("errorMessage");

        assertEquals(errorMessage, ArticleService.VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    public void getArticleDetailView() throws Exception {

        String expectedUrl = "/article/detail";

        // --GIVEN
        //User Set
        String authorName = "testUser";
        User author = new User();
        author.setName(authorName);


        //Article Set
        Long articleId = 1L;
        String articleTitle = "testTitle";
        String articleContent = "testContent";
        Article article = new Article();
        article.setId(articleId);
        article.setAuthor(author);
        article.setTitle(articleTitle);
        article.setContent(articleContent);

        when(articleService.findById(articleId)).thenReturn(article);

        // --WHEN, THEN
        MvcResult result = mockMvc.perform(get("/article/"+articleId))
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("article")).andReturn();

        Article articleResult = (Article) result.getModelAndView().getModel().get("article");

        assertEquals(articleId, article.getId());
        assertEquals(authorName, article.getAuthor().getName());
        assertEquals(articleTitle, article.getTitle());
        assertEquals(articleContent, article.getContent());

    }

    @Test
    public void getArticleDetailViewRequestWithInvalidParameter() throws Exception {

        String expectedUrl = "/article/list";

        long articleId = 1;
        when(articleService.findById(articleId)).thenReturn(null);

        mockMvc.perform(get("/article/" + articleId))
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    public void modifyArticle() throws Exception {

        //GIVEN
        String testTitle = "testTitle";
        String testContent = "testContent";

        Article requestArticle = new Article(null, testTitle, testContent);
        requestArticle.setId(1);

        when(articleService.modify(any(Article.class))).thenReturn(requestArticle);

        //WHEN, THEN
        mockMvc.perform(put("/article/"+requestArticle.getId())
                        .param("title", testTitle)
                        .param("content", testContent)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article/" + requestArticle.getId()));
    }

    @Test
    public void modifyArticleWithWrongData() throws Exception {

        //GIVEN
        String testTitle = "";
        String testContent = "";
        int articleId = 1;

        when(articleService.modify(any(Article.class))).thenThrow(new ArticleModificationException(ArticleService.VALIDATION_EXCEPTION_MESSAGE));

        String expectedUrl = "/article/register";
        //WHEN, THEN
        MvcResult mvcResult = mockMvc.perform(put("/article/" + articleId)
                        .param("title", testTitle)
                        .param("content", testContent)
        )
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(forwardedUrl(WebConfig.RESOLVER_PREFIX + expectedUrl + WebConfig.RESOLVER_SUFFIX))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("article", "errorMessage")).andReturn();

        String errorMessage = (String) mvcResult.getModelAndView().getModel().get("errorMessage");
        assertEquals(errorMessage, ArticleService.VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    public void deleteArticle() throws Exception {

        Long articleId = 1L;

        mockMvc.perform(delete("/article/"+articleId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article"));
    }

    @Test
    public void deleteArticleWithWrongArticleNumber() throws Exception {

        int articleId = -1;
        mockMvc.perform(delete("/article/"+articleId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article"));
    }

    @Test
    public void deleteArticleWithDidNotExistArticle() throws Exception {

        Long articleId = 1L;

        Mockito.doThrow(new ArticleNotFoundException()).when(articleService).delete(articleId);

        mockMvc.perform(delete("/article/"+articleId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article"));
    }
}