package ru.skubatko.dev.ees.ui.feign;

import ru.skubatko.dev.ees.ui.exceptions.FallbackException;
import ru.skubatko.dev.ees.ui.feign.dto.CriterionDto;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "criteria", fallbackFactory = CriteriaResourceFeignClient.ClientFallbackFactory.class)
public interface CriteriaResourceFeignClient {

    @HystrixCommand
    @PostMapping("/criteria")
    void save(@RequestBody CriterionDto dto);

    @Slf4j
    @Component
    class ClientFallbackFactory implements FallbackFactory<CriteriaResourceFeignClient> {

        @Override
        public CriteriaResourceFeignClient create(Throwable cause) {
            log.error("Hystrix Fallback is activated: {}", cause.getMessage(), cause);
            return dto -> {
                log.warn("Using fallback method for save with CriterionDto = {}", dto);
                throw new FallbackException("CriteriaResourceFeignClientFallback is activated");
            };
        }
    }
}
