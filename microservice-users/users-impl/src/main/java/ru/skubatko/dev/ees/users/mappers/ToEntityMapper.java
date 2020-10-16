package ru.skubatko.dev.ees.users.mappers;

import ru.skubatko.dev.ees.users.domain.User;
import ru.skubatko.dev.ees.users.dto.UserDto;

import org.springframework.stereotype.Component;

@Component
public class ToEntityMapper implements Mapper<UserDto, User> {

    @Override
    public User map(UserDto dto) {
        return new User(
                dto.getName(),
                dto.getPassword(),
                dto.getIsActive(),
                dto.getRoles()
        );
    }
}
