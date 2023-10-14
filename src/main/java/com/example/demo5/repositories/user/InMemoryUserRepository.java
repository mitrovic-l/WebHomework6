package com.example.demo5.repositories.user;

import com.example.demo5.entities.User;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryUserRepository implements UserRepository {

    private static List<User> USERS = new CopyOnWriteArrayList<>();

    static {
        //TODO: dodati korisnike ovde, admin-a i Luka
        USERS.add( new User("admin", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918")); //admin
        USERS.add( new User("luka", "d580754468330f66e5c155e16968a2be2a7828eca0885b42a0ce356594bfeab9")); //luka
    }

    @Override
    public User findUser(String username) {
        User user = null;
        Iterator<User> userIterator = USERS.iterator();
        while (userIterator.hasNext() && user == null) {
            User u = userIterator.next();
            if (u.getUsername().equals(username)) {
                user = u;
            }
        }
        return user;
    }

    @Override
    public User addUser(User user) {
        USERS.add(user);
        return user;
    }
}
