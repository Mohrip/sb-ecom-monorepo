package com.StackShop.project.Security;

import java.util.List;

public class UserInfoResponse {
    private Integer userId;
    private String jwtToken;
    private String username;
    private List<String> roles;

    public UserInfoResponse(Integer userId, String username, List<String> roles, String jwtToken) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;

    }


    public Integer getUserId() {
        return userId;
    }
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}


