package ru.skubatko.dev.ees.users.service;

import ru.skubatko.dev.ees.users.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByName(String name);

    List<User> findAll();

    void save(User user);

    void update(String name, User user);

    void deleteByName(String name);
}
