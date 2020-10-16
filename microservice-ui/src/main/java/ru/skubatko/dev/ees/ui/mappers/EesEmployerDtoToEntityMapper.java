package ru.skubatko.dev.ees.ui.mappers;

import ru.skubatko.dev.ees.ui.domain.EesEmployer;
import ru.skubatko.dev.ees.ui.dto.EesEmployerDto;
import ru.skubatko.dev.ees.ui.repository.EesUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EesEmployerDtoToEntityMapper implements Mapper<EesEmployerDto, EesEmployer> {

    private final EesUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public EesEmployer map(EesEmployerDto dto) {
        EesEmployer employer = new EesEmployer();
        employer.setName(dto.getName());
        userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName())
                .ifPresent(employer::setUser);
        return employer;
    }
}
