package ru.skubatko.dev.ees.ui.repository;

import ru.skubatko.dev.ees.ui.domain.EesCriterion;
import ru.skubatko.dev.ees.ui.domain.EesEmployer;
import ru.skubatko.dev.ees.ui.domain.EesRating;
import ru.skubatko.dev.ees.ui.domain.EesUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface EesRatingRepository extends JpaRepository<EesRating, Long> {

    Optional<EesRating> findByUserAndEmployerAndCriterion(EesUser user, EesEmployer employer, EesCriterion criterion);

    Set<EesRating> findAllByUserAndEmployer(EesUser user, EesEmployer employer);
}
