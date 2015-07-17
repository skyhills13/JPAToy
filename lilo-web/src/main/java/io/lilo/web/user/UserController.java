package io.lilo.web.user;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.lilo.domain.User;
import io.lilo.dto.Authentication;
import io.lilo.exception.AlreadyExistException;
import io.lilo.exception.PasswordMismatchException;
import io.lilo.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * Created by kws on 15. 3. 31..
 */
@RequestMapping("/user")
@Controller
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(User user){
        return "/user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User user, Model model){


        try {
            //TODO AutoLogin and redirect to list page
            userService.create(user);
            return "redirect:/user/login";

        } catch (IllegalArgumentException e) {
            //TODO Detail ErrorMessage (age, name, id, password length etc)
            model.addAttribute("errorMessage", "입력값을 다시 확인해 주세요");
            logger.error("Invalid Argument- Email : {}, Name : {}, Age : {}, ", user.getEmail(), user.getName(), user.getAge());

        } catch (AlreadyExistException e) {
            model.addAttribute("errorMessage", "이미 존재하는 아이디입니다.");
        }

        return "/user/register";
    }

    @RequestMapping(value = "/login")
    public String loginForm() {
        return "/user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Authentication authentication, HttpSession session, Model model) {

        try {
            userService.login(authentication);
            session.setAttribute("id", authentication.getId());
            System.out.println("test : "+session.getAttribute("id"));
            return "redirect:/";

        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "아이디를 다시 확인해주세요");
            logger.error("Login Request : {}", e.getMessage());

        } catch (PasswordMismatchException e) {
            model.addAttribute("errorMessage", "비밀번호를 다시 확인해주세요");
            logger.error("Login Request : PasswordMismatchException");

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "잘못된 접근입니다");
            logger.error("Login Request : {}", authentication.toString());
        }

        return "/user/login";
    }

}