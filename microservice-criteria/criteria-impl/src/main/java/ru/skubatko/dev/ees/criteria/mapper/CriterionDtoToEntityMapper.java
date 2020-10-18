package ru.skubatko.dev.ees.criteria.mapper;

import ru.skubatko.dev.ees.criteria.domain.Criterion;
import ru.skubatko.dev.ees.criteria.dto.CriterionDto;

import org.springframework.stereotype.Component;

@Component
public class CriterionDtoToEntityMapper implements Mapper<CriterionDto, Criterion> {

    @Override
    public Criterion map(CriterionDto criterionDto) {
        return new Criterion(criterionDto.getName());
    }
}
