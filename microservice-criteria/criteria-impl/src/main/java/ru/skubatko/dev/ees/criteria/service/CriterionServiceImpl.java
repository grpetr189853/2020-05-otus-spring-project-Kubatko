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
        emulateDbServiceDelay();
        repository.save(toEntityMapper.map(dto));
        emulateFeignServiceDelay();
    }

    @SuppressWarnings("unused")
    public void buildFallbackSaveCriterion(CriterionDto dto) {
        log.warn("buildFallbackSaveCriterion() - verdict: criterion cannot be saved for dto = {}", dto);
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
