package ru.skubatko.dev.ees.users.mapper;

import ru.skubatko.dev.ees.users.domain.User;
import ru.skubatko.dev.ees.users.dto.UserDto;

import org.springframework.stereotype.Component;

@Component
public class ToDtoMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto map(User user) {
        return new UserDto()
                       .setName(user.getName())
                       .setPassword(user.getPassword())
                       .setIsActive(user.getIsActive())
                       .setRoles(user.getRoles())
                ;
    }
}
