package ru.skubatko.dev.ees.ui.mappers;

import ru.skubatko.dev.ees.ui.domain.EesUser;
import ru.skubatko.dev.ees.ui.dto.EesUserDto;

import org.springframework.stereotype.Component;

@Component
public class EesUserToDtoMapper implements Mapper<EesUser, EesUserDto> {

    @Override
    public EesUserDto map(EesUser user) {
        return new EesUserDto()
                       .setName(user.getName())
                ;
    }
}
