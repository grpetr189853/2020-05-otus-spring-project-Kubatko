package ru.skubatko.dev.ees.ui.service;

import ru.skubatko.dev.ees.ui.domain.EesCriterion;
import ru.skubatko.dev.ees.ui.domain.EesEmployer;
import ru.skubatko.dev.ees.ui.domain.EesRating;
import ru.skubatko.dev.ees.ui.domain.EesUser;
import ru.skubatko.dev.ees.ui.dto.EesCriterionDto;
import ru.skubatko.dev.ees.ui.dto.EesEmployerDto;
import ru.skubatko.dev.ees.ui.mappers.EesEmployerDtoToEntityMapper;
import ru.skubatko.dev.ees.ui.mappers.EesEmployerToDtoMapper;
import ru.skubatko.dev.ees.ui.repository.EesCriterionRepository;
import ru.skubatko.dev.ees.ui.repository.EesEmployerRepository;
import ru.skubatko.dev.ees.ui.repository.EesRatingRepository;
import ru.skubatko.dev.ees.ui.repository.EesUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EesEmployerServiceImpl implements EesEmployerService {

    private final EesEmployerRepository employerRepository;
    private final EesCriterionRepository criterionRepository;
    private final EesRatingRepository ratingRepository;
    private final EesUserRepository userRepository;
    private final EesEmployerToDtoMapper toDtoMapper;
    private final EesEmployerDtoToEntityMapper toEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public EesEmployerDto getByName(String name) {
        Optional<EesUser> userOptional =
                userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userOptional.isEmpty()) {
            return null;
        }

        EesUser user = userOptional.get();

        Optional<EesEmployer> employerOptional = employerRepository.findByNameAndUser(name, user);
        if (employerOptional.isEmpty()) {
            return null;
        }

        EesEmployer employer = employerOptional.get();

        EesEmployerDto employerDto = new EesEmployerDto();
        employerDto.setName(employer.getName());
        employerDto.setCriteria(
                employer.getRatings().stream()
                        .map(rating -> new EesCriterionDto(
                                rating.getCriterion().getName(),rating.getCriterion().getWeight(), rating.getRating()))
                        .collect(Collectors.toList()));
        return employerDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EesEmployerDto> findAllByUser(String userName) {
        Optional<EesUser> user = userRepository.findByName(userName);
        if (user.isEmpty()) {
            return Collections.emptyList();
        }

        return employerRepository.findAllByUser(user.get()).stream()
                       .map(toDtoMapper::map)
                       .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(EesEmployerDto employerDto) {
        EesEmployer employer = employerRepository.save(toEntityMapper.map(employerDto));

        EesUser user = employer.getUser();

        employerDto.getCriteria().forEach(criterionDto -> {
            Optional<EesCriterion> criterionOptional = criterionRepository.findByNameAndUser(criterionDto.getName(), user);
            if (criterionOptional.isEmpty()) {
                return;
            }

            EesCriterion criterion = criterionOptional.get();

            ratingRepository.save(new EesRating(user, employer, criterion, criterionDto.getRating()));
        });
    }

    @Override
    @Transactional
    public void updateEmployer(String name, EesEmployerDto employerDto) {
        Optional<EesUser> userOptional =
                userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userOptional.isEmpty()) {
            return;
        }

        EesUser user = userOptional.get();

        Optional<EesEmployer> employerOptional = employerRepository.findByNameAndUser(name, user);
        if (employerOptional.isEmpty()) {
            return;
        }

        EesEmployer employer = employerOptional.get();
        employer.setName(employerDto.getName());
        employerRepository.save(employer);

        employerDto.getCriteria().forEach(
                criterionDto -> {
                    Optional<EesCriterion> criterionOptional =
                            criterionRepository.findByNameAndUser(criterionDto.getName(), user);
                    if (criterionOptional.isEmpty()) {
                        return;
                    }

                    EesCriterion criterion = criterionOptional.get();

                    Optional<EesRating> ratingOptional =
                            ratingRepository.findByUserAndEmployerAndCriterion(user, employer, criterion);
                    if (ratingOptional.isEmpty()) {
                        return;
                    }

                    EesRating rating = ratingOptional.get();
                    rating.setRating(criterionDto.getRating());
                    ratingRepository.save(rating);
                });
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        employerRepository.deleteByName(name);
    }
}
