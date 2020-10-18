package ru.skubatko.dev.ees.ui.feign;

import ru.skubatko.dev.ees.ui.exceptions.FallbackException;
import ru.skubatko.dev.ees.ui.feign.dto.EmployerDto;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "employers", fallbackFactory = EmployersResourceFeignClient.ClientFallbackFactory.class)
public interface EmployersResourceFeignClient {

    @PostMapping("/employers")
    void save(@RequestBody EmployerDto dto);

    @Slf4j
    @Component
    class ClientFallbackFactory implements FallbackFactory<EmployersResourceFeignClient> {

        @Override
        public EmployersResourceFeignClient create(Throwable cause) {
            log.error("Hystrix Fallback is activated: {}", cause.getMessage(), cause);
            return dto -> {
                log.warn("Using fallback method for save with EmployerDto = {}", dto);
                throw new FallbackException("EmployersResourceFeignClientFallback is activated");
            };
        }
    }
}
