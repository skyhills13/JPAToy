package io.lilo.service;

import io.lilo.dto.Authentication;
import io.lilo.exception.AlreadyExistException;
import io.lilo.exception.PasswordMismatchException;
import io.lilo.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.lilo.domain.User;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
    public void login(Authentication authentication) throws NotFoundException, PasswordMismatchException, IllegalArgumentException {
        if (!authentication.isValid())
            throw new IllegalArgumentException();

        User user = userRepository.findByEmail(authentication.getId());

        if (!authentication.isMathchId(user))
            throw new NotFoundException("Authentication Id mismatch or does not exist");

        if (!authentication.isMatchPassword(user))
            throw new PasswordMismatchException();
    }

    public User create(User user) throws AlreadyExistException, IllegalArgumentException {

        if (!user.canRegistable()) {
            throw new IllegalArgumentException();
        }

        User selectedUser = userRepository.findByEmail(user.getEmail());

        if (selectedUser != null)
            throw new AlreadyExistException();

        return userRepository.save(user);
    }
}
