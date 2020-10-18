package ru.skubatko.dev.ees.criteria.service;

import ru.skubatko.dev.ees.criteria.dto.CriterionDto;
import ru.skubatko.dev.ees.criteria.mapper.CriterionDtoToEntityMapper;
import ru.skubatko.dev.ees.criteria.repository.CriterionRepository;

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
public class CriterionServiceImpl implements CriterionService {

    private final CriterionRepository repository;
    private final CriterionDtoToEntityMapper toEntityMapper;

    @HystrixCommand(commandKey = "saveCriterionKey", fallbackMethod = "buildFallback")
    @Transactional
    @Override
    public void save(CriterionDto dto) {
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
