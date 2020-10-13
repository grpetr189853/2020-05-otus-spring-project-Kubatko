package ru.skubatko.dev.ees.employers.resource;

import ru.skubatko.dev.ees.employers.dto.EmployerDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface EmployersResource {

    @PostMapping("/employers")
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestBody EmployerDto dto);
}
