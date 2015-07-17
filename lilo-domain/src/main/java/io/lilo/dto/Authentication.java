package io.lilo.dto;

import org.springframework.util.StringUtils;
import io.lilo.domain.User;

public class Authentication {

    private String id;
    private String password;

    public Authentication(){}

    public Authentication(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return StringUtils.isEmpty(id) == false && StringUtils.isEmpty(password) == false;
    }

    public String getId() {
        return id;
    }

    public boolean isMathchId(User user) {
        return user == null ? false : isMatch(user.getEmail(), this.id);
    }

    public boolean isMatchPassword(User user) {
        return user == null ? false : isMatch(user.getPassword(), this.password);
    }

    private boolean isMatch(String expected, String actual) {

        if (expected == null || actual == null)
            return false;

        return expected.equals(actual);
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
