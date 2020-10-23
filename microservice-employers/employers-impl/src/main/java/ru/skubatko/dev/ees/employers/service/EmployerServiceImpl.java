package ru.skubatko.dev.ees.employers.service;

import ru.skubatko.dev.ees.employers.dto.EmployerDto;
import ru.skubatko.dev.ees.employers.mapper.EmployerDtoToEntityMapper;
import ru.skubatko.dev.ees.employers.repository.EmployerRepository;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository repository;
    private final EmployerDtoToEntityMapper toEntityMapper;

    @HystrixCommand(commandKey = "saveEmployerKey", fallbackMethod = "buildFallbackSaveEmployer")
    @Transactional
    @Override
    public void save(EmployerDto dto) {
        emulateDbServiceDelay();
        repository.save(toEntityMapper.map(dto));
        emulateFeignServiceDelay();
    }

    @SuppressWarnings("unused")
    public void buildFallbackSaveEmployer(EmployerDto dto) {
        log.warn("buildFallbackSaveEmployer() - verdict: employer cannot be saved for dto = {}", dto);
        throw new RuntimeException();
    }

    @SneakyThrows
    private void emulateDbServiceDelay() {
        Thread.sleep(new Random().nextInt(3000));
    }

    @SneakyThrows
    private void emulateFeignServiceDelay() {
        Thread.sleep(new Random().nextInt(4000));
    }
}
