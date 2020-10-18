package ru.skubatko.dev.ees.ui.repository;

import ru.skubatko.dev.ees.ui.domain.EesCriterion;
import ru.skubatko.dev.ees.ui.domain.EesUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EesCriterionRepository extends JpaRepository<EesCriterion, Long> {

    List<EesCriterion> findByUser(EesUser user);

    Optional<EesCriterion> findByNameAndUser(String name, EesUser user);

    void deleteByName(String name);
}
