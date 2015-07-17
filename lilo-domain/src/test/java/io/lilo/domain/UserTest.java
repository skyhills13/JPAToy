package io.lilo.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    String email = "";
    String name = "";
    int age = 0;

    User testUser;

    @Before
    public void setUp(){
        testUser = new User("asdf@naver.com", "asdf", "박소은", 26);
    }

    @Test
    public void 올바른_파라미터를_이용한_가입가능여부_확인() {
        assertTrue(testUser.canRegistable());
    }

    @Test
    public void 이메일정합성_확인() {
        assertTrue(testUser.checkEmail());

        testUser.setEmail("");
        assertFalse(testUser.checkEmail());

        testUser.setEmail("test");
        assertFalse(testUser.checkEmail());
    }

    @Test
    public void 유저패스워드_확인(){
        assertTrue(testUser.IsValidPassword());

        testUser.setPassword("");
        assertFalse(testUser.IsValidPassword());
    }

    @Test
    public void 유저이름_확인() {
        assertTrue(testUser.IsValidName());
        testUser.setName("");
        assertFalse(testUser.IsValidName());
    }

    @Test
    public void 유저나이_확인() {
        assertTrue(testUser.IsValidAge());
        testUser.setAge(0);

        assertFalse(testUser.IsValidAge());
        testUser.setAge(-1);

        assertFalse(testUser.IsValidAge());
    }

}
