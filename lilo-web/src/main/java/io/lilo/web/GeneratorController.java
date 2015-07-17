package io.lilo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.lilo.domain.Article;
import io.lilo.domain.User;
import io.lilo.exception.ArticleCreationException;
import io.lilo.service.ArticleService;
import io.lilo.service.SessionService;

@Controller
public class GeneratorController {

    @Autowired
    ArticleService articleService;

    @Autowired
    SessionService sessionService;

    @RequestMapping("/generate")
    public @ResponseBody String generateMockData() throws ArticleCreationException {

        int maxNum = 100;

        User author = sessionService.getCurrentUser();

        for (int i = 1 ; i <=  maxNum; ++i) {
            articleService.create(new Article(author, "TestTitle_"+i, "TestContent_"+i));
        }

        return "success";
    }
}
