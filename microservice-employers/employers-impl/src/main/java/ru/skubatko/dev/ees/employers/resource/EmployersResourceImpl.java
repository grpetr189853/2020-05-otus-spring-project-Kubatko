package ru.skubatko.dev.ees.employers.resource;

import ru.skubatko.dev.ees.employers.dto.EmployerDto;
import ru.skubatko.dev.ees.employers.service.EmployerService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployersResourceImpl implements EmployersResource {

    private final EmployerService service;

    @Override
    @PostMapping("/employers")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody EmployerDto dto) {
        service.save(dto);
    }
}
