package ru.skubatko.dev.ees.ui.service;

import ru.skubatko.dev.ees.ui.domain.EesUser;
import ru.skubatko.dev.ees.ui.dto.EesRatingDto;
import ru.skubatko.dev.ees.ui.repository.EesEmployerRepository;
import ru.skubatko.dev.ees.ui.repository.EesRatingRepository;
import ru.skubatko.dev.ees.ui.repository.EesUserRepository;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EesRatingServiceImpl implements EesRatingService {

    private final EesUserRepository userRepository;
    private final EesEmployerRepository employerRepository;
    private final EesRatingRepository ratingRepository;

    @HystrixCommand(commandKey = "findRatingsKey", fallbackMethod = "buildFallbackFindRatings")
    @Transactional(readOnly = true)
    @Override
    public List<EesRatingDto> findByUserDesc(String userName) {
        emulateServiceDelay();

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

    @SuppressWarnings("unused")
    public List<EesRatingDto> buildFallbackFindRatings(String userName) {
        log.warn("buildFallbackRatings() - verdict: ratings service failed for userName = {}", userName);
        return Collections.emptyList();
    }

    @SneakyThrows
    private void emulateServiceDelay() {
        Thread.sleep(3000);
    }
}
