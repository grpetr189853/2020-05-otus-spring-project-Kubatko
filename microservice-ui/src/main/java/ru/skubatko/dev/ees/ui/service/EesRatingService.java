package ru.skubatko.dev.ees.ui.service;

import ru.skubatko.dev.ees.ui.dto.EesRatingDto;

import java.util.List;

public interface EesRatingService {

    List<EesRatingDto> findByUserDesc(String userName);
}
