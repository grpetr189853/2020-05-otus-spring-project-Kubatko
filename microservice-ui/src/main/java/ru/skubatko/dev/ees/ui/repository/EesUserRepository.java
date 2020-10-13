package ru.skubatko.dev.ees.ui.repository;

import ru.skubatko.dev.ees.ui.domain.EesUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EesUserRepository extends JpaRepository<EesUser, Long> {

    Optional<EesUser> findByName(String name);
}
