package ru.skubatko.dev.ees.criteria.resource;

import ru.skubatko.dev.ees.criteria.dto.CriterionDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CriteriaResource {

    @PostMapping("/criteria")
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestBody CriterionDto dto);
}
