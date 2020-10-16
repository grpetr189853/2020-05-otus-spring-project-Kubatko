package ru.skubatko.dev.ees.ui.mappers;

import ru.skubatko.dev.ees.ui.dto.EesEmployerDto;
import ru.skubatko.dev.ees.ui.feign.dto.EmployerDto;

import org.springframework.stereotype.Component;

@Component
public class EesEmployerDtoToResourceMapper implements Mapper<EesEmployerDto, EmployerDto> {

    @Override
    public EmployerDto map(EesEmployerDto dto) {
        return new EmployerDto()
                       .setName(dto.getName())
                ;
    }
}
