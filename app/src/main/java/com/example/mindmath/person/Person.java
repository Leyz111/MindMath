package com.example.mindmath.person;

import com.google.gson.annotations.SerializedName;

public class Person {
    private String name;
    private String role;
    private String login;
    private String password;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @SerializedName("top_result")
    private String topResult;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopResult() {
        return topResult;
    }

    public void setTopResult(String topResult) {
        this.topResult = topResult;
    }
}
