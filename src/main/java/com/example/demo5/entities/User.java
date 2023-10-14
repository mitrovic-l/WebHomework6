package com.example.demo5.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class User {
    private Integer id;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public User(Integer id, String username, String password) {
        this(username, password);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
