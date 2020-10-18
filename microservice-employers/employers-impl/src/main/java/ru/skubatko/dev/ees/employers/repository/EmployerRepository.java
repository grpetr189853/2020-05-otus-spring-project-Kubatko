package ru.skubatko.dev.ees.employers.repository;

import ru.skubatko.dev.ees.employers.domain.Employer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    Optional<Employer> findByName(String name);
}
