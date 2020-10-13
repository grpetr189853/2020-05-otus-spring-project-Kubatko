package ru.skubatko.dev.ees.criteria.service;

import ru.skubatko.dev.ees.criteria.dto.CriterionDto;
import ru.skubatko.dev.ees.criteria.mapper.CriterionDtoToEntityMapper;
import ru.skubatko.dev.ees.criteria.repository.CriterionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriterionServiceImpl implements CriterionService {

    private final CriterionRepository repository;
    private final CriterionDtoToEntityMapper toEntityMapper;

    @Override
    public void save(CriterionDto dto) {
        repository.save(toEntityMapper.map(dto));
    }
}
