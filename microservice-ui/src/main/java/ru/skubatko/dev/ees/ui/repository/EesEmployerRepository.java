package ru.skubatko.dev.ees.ui.repository;

import ru.skubatko.dev.ees.ui.domain.EesEmployer;
import ru.skubatko.dev.ees.ui.domain.EesUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface EesEmployerRepository extends JpaRepository<EesEmployer, Long> {

    Optional<EesEmployer> findByNameAndUser(String name, EesUser user);

    Set<EesEmployer> findAllByUser(EesUser user);

    void deleteByName(String name);
}
