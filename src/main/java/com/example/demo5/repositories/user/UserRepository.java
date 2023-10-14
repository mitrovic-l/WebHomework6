package com.example.demo5.repositories.user;

import com.example.demo5.entities.User;

public interface UserRepository{
    public User findUser(String username);
    public User addUser(User user);
}
