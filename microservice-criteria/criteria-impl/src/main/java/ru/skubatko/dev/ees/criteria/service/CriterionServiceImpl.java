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

    @HystrixCommand(commandKey = "saveCriterionKey", fallbackMethod = "buildFallbackSaveCriterion")
    @Transactional
    @Override
    public void save(CriterionDto dto) {
        repository.save(toEntityMapper.map(dto));
        emulateServiceDelay();
    }

    @SuppressWarnings("unused")
    public void buildFallbackSaveCriterion(CriterionDto dto) {
        log.warn("buildFallbackSaveCriterion() - verdict: criterion cannot be saved for dto = {}", dto);
        throw new RuntimeException();
    }

    @SneakyThrows
    private void emulateServiceDelay() {
        int delay = new Random(System.currentTimeMillis()).nextInt(7) * 1000;
        log.trace("emulateServiceDelay() - trace: delay (feign<4500, db<5500) = {}", delay);
        Thread.sleep(delay);
    }
}
