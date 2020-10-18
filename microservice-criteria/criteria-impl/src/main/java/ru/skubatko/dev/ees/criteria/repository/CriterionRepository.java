package ru.skubatko.dev.ees.criteria.repository;

import ru.skubatko.dev.ees.criteria.domain.Criterion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CriterionRepository extends JpaRepository<Criterion, Long> {

    Optional<Criterion> findByName(String name);
}
