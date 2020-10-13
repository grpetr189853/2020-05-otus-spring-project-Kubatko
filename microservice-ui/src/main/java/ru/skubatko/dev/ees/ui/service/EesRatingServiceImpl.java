package ru.skubatko.dev.ees.ui.service;

import ru.skubatko.dev.ees.ui.domain.EesUser;
import ru.skubatko.dev.ees.ui.dto.EesRatingDto;
import ru.skubatko.dev.ees.ui.repository.EesEmployerRepository;
import ru.skubatko.dev.ees.ui.repository.EesRatingRepository;
import ru.skubatko.dev.ees.ui.repository.EesUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EesRatingServiceImpl implements EesRatingService {

    private final EesUserRepository userRepository;
    private final EesEmployerRepository employerRepository;
    private final EesRatingRepository ratingRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EesRatingDto> findByUserDesc(String userName) {
        Optional<EesUser> userOptional = userRepository.findByName(userName);
        if (userOptional.isEmpty()) {
            return Collections.emptyList();
        }

        EesUser user = userOptional.get();

        return employerRepository.findAllByUser(user).stream()
                       .map(employer -> new EesRatingDto()
                                                .setEmployerName(employer.getName())
                                                .setEmployerRating(
                                                        ratingRepository.findAllByUserAndEmployer(user, employer).stream()
                                                                .mapToInt(rating -> rating.getCriterion().getWeight() * rating.getRating())
                                                                .sum()))
                       .sorted(Comparator.comparingInt(EesRatingDto::getEmployerRating).reversed())
                       .collect(Collectors.toList())
                ;
    }
}
