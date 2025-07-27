package br.com.systec.taskflow.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serial;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private String type;
    private String profile;
    private Long userId;
    private Long tenantId;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String type) {
        this.token = token;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

}
