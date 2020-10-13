package ru.skubatko.dev.ees.ui.feign;

import ru.skubatko.dev.ees.ui.feign.dto.CriterionDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "criteria")
public interface CriterionResourceFeignClient {

    @PostMapping("/criteria")
    void save(@RequestBody CriterionDto dto);
}
