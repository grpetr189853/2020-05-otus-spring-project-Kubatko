package ru.skubatko.dev.ees.ui.mappers;

import ru.skubatko.dev.ees.ui.dto.EesCriterionDto;
import ru.skubatko.dev.ees.ui.feign.dto.CriterionDto;

import org.springframework.stereotype.Component;

@Component
public class EesCriterionDtoToResourceMapper implements Mapper<EesCriterionDto, CriterionDto> {

    @Override
    public CriterionDto map(EesCriterionDto dto) {
        return new CriterionDto()
                       .setName(dto.getName())
                ;
    }
}
