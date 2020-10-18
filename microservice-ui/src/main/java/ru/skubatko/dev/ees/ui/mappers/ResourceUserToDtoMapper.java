package ru.skubatko.dev.ees.ui.mappers;

import ru.skubatko.dev.ees.ui.dto.EesUserDto;
import ru.skubatko.dev.ees.ui.feign.dto.UserDto;

import org.springframework.stereotype.Component;

@Component
public class ResourceUserToDtoMapper implements Mapper<UserDto, EesUserDto> {

    @Override
    public EesUserDto map(UserDto user) {
        return new EesUserDto()
                       .setName(user.getName())
                       .setPassword(user.getPassword())
                       .setIsActive(user.getIsActive())
                       .setRoles(user.getRoles())
                ;
    }
}
