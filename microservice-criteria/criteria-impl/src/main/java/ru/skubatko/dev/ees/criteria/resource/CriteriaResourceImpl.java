package ru.skubatko.dev.ees.criteria.resource;

import ru.skubatko.dev.ees.criteria.dto.CriterionDto;
import ru.skubatko.dev.ees.criteria.service.CriterionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CriteriaResourceImpl implements CriteriaResource {

    private final CriterionService service;

    @Override
    @PostMapping("/criteria")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody CriterionDto dto) {
        service.save(dto);
    }
}
