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

    @HystrixCommand(commandKey = "saveEmployerKey", fallbackMethod = "buildFallback")
    @Transactional
    @Override
    public void save(EmployerDto dto) {
        emulateServiceDelay();
        repository.save(toEntityMapper.map(dto));
    }

    @SuppressWarnings("unused")
    public void buildFallback() {
        log.warn("buildFallback() - verdict: user service is unavailable");
    }

    @SneakyThrows
    private void emulateServiceDelay() {
        Thread.sleep(1000 + new Random().nextInt(4000));
    }
}
