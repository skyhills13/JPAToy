package io.lilo.service;

import io.lilo.config.DBConfig;
import io.lilo.dto.Authentication;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import io.lilo.domain.User;
import io.lilo.exception.AlreadyExistException;
import io.lilo.exception.PasswordMismatchException;
import io.lilo.repository.UserRepository;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;


    User testUser;

    @Before
    public void setUp() {
        this.testUser = new User();

        String email = "testEmail@naver.com";
        String name = "testName";
        String password = "testPassword";
        int age = 28;

        testUser.setEmail(email);
        testUser.setName(name);
        testUser.setPassword(password);
        testUser.setAge(age);
    }


    @Test
    public void registerWithValidParameter() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(null);

        try {
            userService.create(testUser);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Must not reach here");
        }
    }

    @Test(expected = AlreadyExistException.class)
    public void registerWithAlreadyExistUserEmail() throws AlreadyExistException {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);
        userService.create(testUser);
    }

    @Test
    public void registerWithInvalidParameter() throws AlreadyExistException {

        testUser.setEmail(null);

        try {
            userService.create(testUser);
            fail("Must not reach here");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void loginWithValidParameter() {

        String id = "testId";
        String password = "testPassword";

        User user = new User();
        user.setEmail(id);
        user.setPassword(password);

        when(userRepository.findByEmail(id)).thenReturn(user);

        Authentication authentication = new Authentication(id, password);

        try {
            userService.login(authentication);
        } catch (Exception e) {
            fail("unexpected failure");
        }

    }


    //RuntimeException은 expected로 처리되지 않는다.
    @Test
    public void loginWithEmptyAuthentication() {
        Authentication authentication = new Authentication("", "password");

        try {
            userService.login(authentication);
            fail("unexpected failure");
        } catch (IllegalArgumentException e) {
            System.out.println("must here");
        } catch (Exception e) {
            fail("unexpected failure");
        }

        authentication = new Authentication(null, "password");

        try {
            userService.login(authentication);
            fail("unexpected failure");
        } catch (IllegalArgumentException e) {
            System.out.println("must here");
        } catch (Exception e) {
            fail("unexpected failure");
        }
    }

    @Test(expected = PasswordMismatchException.class)
    public void loginWithMismatchPassword() throws NotFoundException, PasswordMismatchException {

        String id = "testEmail";

        Authentication authentication = new Authentication(id, "testPassword");
        User user = new User();
        user.setEmail(id);
        user.setPassword("testUserPassoword");

        when(userRepository.findByEmail(authentication.getId())).thenReturn(user);

        userService.login(authentication);
    }

    @Test(expected = NotFoundException.class)
    public void loginWithNotExistId() throws NotFoundException, PasswordMismatchException {
        Authentication authentication = new Authentication("testEmail", "testPassword");
        when(userRepository.findByEmail(authentication.getId())).thenReturn(null);

        userService.login(authentication);
    }
}
