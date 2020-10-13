package ru.skubatko.dev.ees.users.resources;

import ru.skubatko.dev.ees.users.domain.User;
import ru.skubatko.dev.ees.users.dto.UserDto;
import ru.skubatko.dev.ees.users.mappers.ToDtoMapper;
import ru.skubatko.dev.ees.users.mappers.ToEntityMapper;
import ru.skubatko.dev.ees.users.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UsersResourceImpl implements UsersResource {

    private final UserService service;
    private final ToDtoMapper toDtoMapper;
    private final ToEntityMapper toEntityMapper;

    @Override
    @GetMapping("/users/{name}")
    public Optional<UserDto> findByName(@PathVariable("name") String name) {
        Optional<User> user = service.findByName(name);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(toDtoMapper.map(user.get()));
    }

    @Override
    @GetMapping("/users")
    public List<UserDto> findAll() {
        return service.findAll().stream().map(toDtoMapper::map).collect(Collectors.toList());
    }

    @Override
    @PostMapping("/users")
    public void save(UserDto dto) {
        service.save(toEntityMapper.map(dto));
    }

    @Override
    @PutMapping("/users/{name}")
    public void update(@PathVariable("name") String name, @RequestBody UserDto dto) {
        service.update(name, toEntityMapper.map(dto));
    }

    @Override
    @DeleteMapping("/users/{name}")
    public void deleteByName(@PathVariable("name") String name) {
        service.deleteByName(name);
    }
}
