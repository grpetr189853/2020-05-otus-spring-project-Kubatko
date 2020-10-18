package ru.skubatko.dev.ees.ui.service;

import ru.skubatko.dev.ees.ui.dto.EesEmployerDto;

import java.util.List;

public interface EesEmployerService {

    EesEmployerDto getByName(String name);

    List<EesEmployerDto> findAllByUser(String userName);

    void save(EesEmployerDto employer);

    void updateEmployer(String name, EesEmployerDto employer);

    void deleteByName(String name);
}
