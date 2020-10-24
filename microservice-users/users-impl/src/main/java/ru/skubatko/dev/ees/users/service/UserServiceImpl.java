package ru.skubatko.dev.ees.users.service;

import ru.skubatko.dev.ees.users.domain.User;
import ru.skubatko.dev.ees.users.repository.UserRepository;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @HystrixCommand(commandKey = "findUserByNameKey", fallbackMethod = "buildFallbackFindUser")
    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByName(String name) {
        Optional<User> user = repository.findByName(name);
        emulateServiceDelay();
        return user;
    }

    @SuppressWarnings("unused")
    public Optional<User> buildFallbackFindUser(String name) {
        log.warn("buildFallbackFindUser() - verdict: failed findByName for name = {}", name);
        User user = new User("user", "", false, "USER");
        return Optional.of(user);
    }

    @HystrixCommand(commandKey = "findAllUserKey", fallbackMethod = "buildFallbackFindAllUsers")
    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        List<User> users = repository.findAll();
        emulateServiceDelay();
        return users;
    }

    @SuppressWarnings("unused")
    public List<User> buildFallbackFindAllUsers() {
        log.warn("buildFallbackFindAllUsers() - verdict: failed findAll");
        User user = new User("user", "", false, "USER");
        return List.of(user);
    }

    @HystrixCommand(commandKey = "saveUserKey", fallbackMethod = "buildFallbackSaveUser")
    @Transactional
    @Override
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
        emulateServiceDelay();
    }

    @SuppressWarnings("unused")
    public void buildFallbackSaveUser(User user) {
        log.warn("buildFallbackFindAllUsers() - verdict: failed save user = {}", user);
        throw new RuntimeException();
    }

    @HystrixCommand(commandKey = "updateUserKey", fallbackMethod = "buildFallbackUpdateUser")
    @Transactional
    @Override
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
        emulateServiceDelay();
    }

    @SuppressWarnings("unused")
    public void buildFallbackUpdateUser(String name, User updatedUser) {
        log.warn("buildFallbackFindAllUsers() - verdict: failed update user with name = {}", name);
        throw new RuntimeException();
    }

    @HystrixCommand(commandKey = "deleteUserKey", fallbackMethod = "buildFallbackDeleteUser")
    @Transactional
    @Override
    public void deleteByName(String name) {
        Optional<User> optionalUser = repository.findByName(name);
        if (optionalUser.isEmpty()) {
            return;
        }

        repository.delete(optionalUser.get());
        emulateServiceDelay();
    }

    @SuppressWarnings("unused")
    public void buildFallbackDeleteUser(String name) {
        log.warn("buildFallbackDeleteUser() - verdict: failed delete user with name = {}", name);
        throw new RuntimeException();
    }

    @SneakyThrows
    private void emulateServiceDelay() {
        int delay = new Random(System.currentTimeMillis()).nextInt(7) * 1000;
        log.trace("emulateServiceDelay() - trace: delay (feign<4500, db<5500) = {}", delay);
        Thread.sleep(delay);
    }
}
