package ru.skubatko.dev.ees.ui.feign;

import ru.skubatko.dev.ees.ui.feign.dto.EmployerDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "employers")
public interface EmployerResourceFeignClient {

    @PostMapping("/employers")
    void save(@RequestBody EmployerDto dto);
}
