package ru.skubatko.dev.ees.users.service;

import ru.skubatko.dev.ees.users.domain.User;
import ru.skubatko.dev.ees.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        User newUser = user;
        Optional<User> persistedUserOptional = repository.findByName(user.getName());
        if (persistedUserOptional.isPresent()) {
            newUser = persistedUserOptional.get();
            newUser.setPassword(user.getPassword());
            newUser.setIsActive(user.getIsActive());
            newUser.setRoles(user.getRoles());
        }

        repository.save(newUser);
    }

    @Override
    @Transactional
    public void update(String name, User updatedUser) {
        Optional<User> userOptional = repository.findByName(name);
        if (userOptional.isEmpty()) {
            return;
        }

        User user = userOptional.get();
        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        user.setIsActive(updatedUser.getIsActive());
        user.setRoles(updatedUser.getRoles());
        repository.save(user);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        Optional<User> optionalUser = repository.findByName(name);
        if (optionalUser.isEmpty()) {
            return;
        }

        repository.delete(optionalUser.get());
    }
}
