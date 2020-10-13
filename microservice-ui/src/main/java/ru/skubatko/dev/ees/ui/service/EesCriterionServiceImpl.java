package ru.skubatko.dev.ees.ui.service;

import ru.skubatko.dev.ees.ui.domain.EesCriterion;
import ru.skubatko.dev.ees.ui.domain.EesEmployer;
import ru.skubatko.dev.ees.ui.domain.EesRating;
import ru.skubatko.dev.ees.ui.domain.EesUser;
import ru.skubatko.dev.ees.ui.dto.EesCriterionDto;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EesCriterionServiceImpl implements EesCriterionService {

    private final EesUserRepository userRepository;
    private final EesEmployerRepository employerRepository;
    private final EesRatingRepository ratingRepository;
    private final EesCriterionRepository criterionRepository;

    @Override
    @Transactional(readOnly = true)
    public EesCriterionDto getByName(String name) {
        Optional<EesUser> userOptional =
                userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userOptional.isEmpty()) {
            return null;
        }

        EesUser user = userOptional.get();

        Optional<EesCriterion> criterionOptional = criterionRepository.findByNameAndUser(name, user);
        if (criterionOptional.isEmpty()) {
            return null;
        }

        EesCriterion criterion = criterionOptional.get();

        return new EesCriterionDto(criterion.getName(), criterion.getWeight());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EesCriterionDto> findByUser(String userName) {
        Optional<EesUser> userOptional = userRepository.findByName(userName);
        if (userOptional.isEmpty()) {
            return Collections.emptyList();
        }

        return criterionRepository.findByUser(userOptional.get()).stream()
                       .map(criterion -> new EesCriterionDto(criterion.getName(), criterion.getWeight()))
                       .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(EesCriterionDto criterionDto) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<EesUser> userOptional = userRepository.findByName(userName);
        if (userOptional.isEmpty()) {
            return;
        }

        EesUser user = userOptional.get();

        EesCriterion criterion = criterionRepository.save(new EesCriterion(criterionDto.getName(), criterionDto.getWeight(), user));

        Set<EesEmployer> employers = employerRepository.findAllByUser(user);
        employers.forEach(employer -> ratingRepository.save(new EesRating(user, employer, criterion, 0)));
    }

    @Override
    public void updateBook(String name, EesCriterionDto criterionDto) {
        Optional<EesUser> userOptional =
                userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userOptional.isEmpty()) {
            return;
        }

        EesUser user = userOptional.get();

        Optional<EesCriterion> criterionOptional = criterionRepository.findByNameAndUser(name, user);
        if (criterionOptional.isEmpty()) {
            return;
        }

        EesCriterion criterion = criterionOptional.get();
        criterion.setName(criterionDto.getName());
        criterion.setWeight(criterionDto.getWeight());
        criterionRepository.save(criterion);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        criterionRepository.deleteByName(name);
    }
}
