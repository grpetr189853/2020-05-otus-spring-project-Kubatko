package ru.skubatko.dev.ees.ui.mapper;

import ru.skubatko.dev.ees.ui.dto.EesUserDto;
import ru.skubatko.dev.ees.ui.feign.dto.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EesUserDtoToResourceMapper implements Mapper<EesUserDto, UserDto> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto map(EesUserDto user) {
        return new UserDto()
                       .setName(user.getName())
                       .setPassword(passwordEncoder.encode(user.getPassword()))
                       .setIsActive(user.getIsActive())
                       .setRoles(user.getRoles())
                ;
    }
}
