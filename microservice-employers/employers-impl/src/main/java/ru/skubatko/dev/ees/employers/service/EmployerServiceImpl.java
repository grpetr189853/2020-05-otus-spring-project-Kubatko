package ru.skubatko.dev.ees.employers.service;

import ru.skubatko.dev.ees.employers.dto.EmployerDto;
import ru.skubatko.dev.ees.employers.mapper.EmployerDtoToEntityMapper;
import ru.skubatko.dev.ees.employers.repository.EmployerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository repository;
    private final EmployerDtoToEntityMapper toEntityMapper;

    @Override
    @Transactional
    public void save(EmployerDto dto) {
        repository.save(toEntityMapper.map(dto));
    }
}
