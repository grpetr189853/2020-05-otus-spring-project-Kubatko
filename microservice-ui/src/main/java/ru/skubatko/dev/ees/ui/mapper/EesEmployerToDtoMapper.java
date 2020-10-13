package ru.skubatko.dev.ees.ui.mapper;

import ru.skubatko.dev.ees.ui.domain.EesEmployer;
import ru.skubatko.dev.ees.ui.dto.EesEmployerDto;

import org.springframework.stereotype.Component;

@Component
public class EesEmployerToDtoMapper implements Mapper<EesEmployer, EesEmployerDto> {

    @Override
    public EesEmployerDto map(EesEmployer employer) {
        return new EesEmployerDto()
                       .setName(employer.getName())
                ;
    }
}
