package io.lilo.web.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.lilo.domain.Comment;
import io.lilo.service.CommentService;

@Controller
@RequestMapping("/article/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private static Logger logger = LoggerFactory.getLogger(CommentController.class);

    //TODO Convert to Ajax Process
    //TODO Try - Catch to detect exception and announce error to user
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String register(Comment comment, Model model) {

        logger.debug("comment : {}", comment.toString());

        try {
            commentService.create(comment);
        } catch (IllegalArgumentException e) {
            //TODO Unused model. convert to ajax and user them
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/article/"+comment.getArticleId();
    }
}
