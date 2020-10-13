package ru.skubatko.dev.ees.ui.service;

import ru.skubatko.dev.ees.ui.dto.EesUserDto;

import java.util.List;
import java.util.Optional;

public interface EesUserService {

    EesUserDto getByName(String name);

    Optional<EesUserDto> findByName(String userName);

    List<EesUserDto> findAll();

    void save(EesUserDto user);

    void update(String name, EesUserDto user);

    void deleteByName(String name);
}
