package ru.skubatko.dev.ees.ui.service;

import ru.skubatko.dev.ees.ui.dto.EesCriterionDto;

import java.util.List;

public interface EesCriterionService {

    EesCriterionDto getByName(String name);

    List<EesCriterionDto> findByUser(String userName);

    void save(EesCriterionDto criterion);

    void updateBook(String name, EesCriterionDto criterion);

    void deleteByName(String name);
}
