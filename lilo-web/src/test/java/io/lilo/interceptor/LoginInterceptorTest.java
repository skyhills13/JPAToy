package io.lilo.interceptor;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.Assert.*;

public class LoginInterceptorTest {

    LoginInterceptor interceptor;

    MockHttpServletRequest request;
    MockHttpServletResponse response;
    MockHttpSession session;

    @Before
    public void setUp() {
        this.interceptor = new LoginInterceptor();
        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();
        this.session = new MockHttpSession();

        this.request.setSession(this.session);
    }

    @Test
    public void 세션값이_존재할때의_interceptor_요청() throws Exception {
        this.session.setAttribute("id", "test");
        assertTrue(interceptor.preHandle(request, response, null));
        assertEquals(null, response.getRedirectedUrl());
    }

    @Test
    public void 세션값이_존재하지_않을때_interceptor_요청() throws Exception {
        assertFalse(interceptor.preHandle(request, response, null));
        assertEquals("/user/login", response.getRedirectedUrl());
    }
}
