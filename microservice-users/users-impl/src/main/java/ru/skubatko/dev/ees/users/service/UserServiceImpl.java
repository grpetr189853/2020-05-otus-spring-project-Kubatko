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

    @HystrixCommand(commandKey = "findUserByNameKey", fallbackMethod = "buildFallbackUser")
    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByName(String name) {
        return repository.findByName(name);
    }

    @SuppressWarnings("unused")
    public Optional<User> buildFallbackUser(String name) {
        User user = new User("user", "", false, "USER");
        return Optional.of(user);
    }

    @HystrixCommand(commandKey = "findAllUserKey", fallbackMethod = "buildFallbackUsers")
    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        emulateServiceDelay();
        return repository.findAll();
    }

    @SuppressWarnings("unused")
    public List<User> buildFallbackUsers() {
        User user = new User("user", "", false, "USER");
        return List.of(user);
    }

    @HystrixCommand(commandKey = "saveUserKey", fallbackMethod = "buildFallback")
    @Transactional
    @Override
    public void save(User user) {
        emulateServiceDelay();
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

    @HystrixCommand(commandKey = "updateUserKey", fallbackMethod = "buildFallback")
    @Transactional
    @Override
    public void update(String name, User updatedUser) {
        emulateServiceDelay();
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

    @HystrixCommand(commandKey = "deleteUserKey", fallbackMethod = "buildFallback")
    @Transactional
    @Override
    public void deleteByName(String name) {
        emulateServiceDelay();
        Optional<User> optionalUser = repository.findByName(name);
        if (optionalUser.isEmpty()) {
            return;
        }

        repository.delete(optionalUser.get());
    }

    @SuppressWarnings("unused")
    public void buildFallback() {
        log.warn("buildFallback() - verdict: user service is unavailable");
    }

    @SneakyThrows
    private void emulateServiceDelay() {
        Thread.sleep(new Random().nextInt(4000));
    }
}
