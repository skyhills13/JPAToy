package io.lilo.service;

import io.lilo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.lilo.domain.User;

import javax.servlet.http.HttpSession;

@Service
public class SessionService {

    @Autowired
    UserRepository userRepository;

    public User getCurrentUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        String id = (String) session.getAttribute("id");

        Assert.notNull(id);

        return userRepository.findByEmail(id);
    }
}