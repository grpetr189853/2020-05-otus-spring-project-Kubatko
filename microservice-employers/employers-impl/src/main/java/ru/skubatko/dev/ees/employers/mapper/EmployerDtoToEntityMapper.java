package ru.skubatko.dev.ees.employers.mapper;

import ru.skubatko.dev.ees.employers.domain.Employer;
import ru.skubatko.dev.ees.employers.dto.EmployerDto;

import org.springframework.stereotype.Component;

@Component
public class EmployerDtoToEntityMapper implements Mapper<EmployerDto, Employer> {

    @Override
    public Employer map(EmployerDto employerDto) {
        Employer employer = new Employer();
        employer.setName(employerDto.getName());
        return employer;
    }
}
