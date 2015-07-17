package io.lilo.web.article;

import io.lilo.domain.Article;
import io.lilo.exception.ArticleCreationException;
import io.lilo.exception.ArticleModificationException;
import io.lilo.exception.ArticleNotFoundException;
import io.lilo.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.AuthenticationException;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/list")
    public String list(Integer page, Model model) {

        if (page == null) {
            page = 1;

        } else if (page <= 0) {
            page = 1;
            model.addAttribute("errorMessage", "잘못된 페이지 요청입니다.");
        }

        model.addAttribute("articles", articleService.findAll());

        return "/article/list";
    }

    @RequestMapping("/register")
    public String creationForm() {
        return "/article/register";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(Article article, Model model) {

        log.debug("Create Request : {}", article.toString());

        try {
            articleService.create(article);
        } catch (ArticleCreationException exception) {
            model.addAttribute("article", article);
            model.addAttribute("errorMessage", exception.getMessage());
            return "/article/register";
        }

        return "redirect:/article/list";
    }

    @RequestMapping(value = "/{id}")
    public String detailView(@PathVariable Long id, Model model) {

        Article article = articleService.findById(id);

        if (article == null) {
            model.addAttribute("errorMessage", "잘못된 접근입니다");
            return "/article/list";
        }

        model.addAttribute("article", article);
        return "/article/detail";
    }

    //TODO Use PathVariable
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String modify(@PathVariable int id, Article article, Model model) {
        article.setId(id);

        try {
            Article modifiedArticle = articleService.modify(article);
            return "redirect:/article/"+modifiedArticle.getId();

        } catch (ArticleModificationException e) {
            model.addAttribute("article", article);
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "/article/register";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {

        try {
            articleService.delete(id);

        //TODO ArticleNotFoundException를 통해 이전의 다른 컨트롤러 에러처리도 리팩토링하자
        } catch (AuthenticationException e) {
            return "redirect:/article/"+id;
        } catch (ArticleNotFoundException e) {
            return "redirect:/article";
        }

        return "redirect:/article";
    }
}
