package br.com.systec.taskflow.security.vo;

import java.io.Serial;
import java.io.Serializable;

public class LoginAuthenticateVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private String type;
    private String profile;
    private Long userId;

    public LoginAuthenticateVO(Long userId, String token, String bearer, String profile) {
        this.userId = userId;
        this.token = token;
        this.type = bearer;
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
