package ru.skubatko.dev.ees.ui.feign;

import ru.skubatko.dev.ees.ui.exceptions.FallbackException;
import ru.skubatko.dev.ees.ui.feign.dto.UserDto;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "users", fallbackFactory = UsersResourceFeignClient.ClientFallbackFactory.class)
public interface UsersResourceFeignClient {

    @GetMapping("/users/{name}")
    Optional<UserDto> findByName(@PathVariable("name") String name);

    @GetMapping("/users")
    List<UserDto> findAll();

    @PostMapping("/users")
    void save(@RequestBody UserDto dto);

    @PutMapping("/users/{name}")
    void update(@PathVariable("name") String name, @RequestBody UserDto dto);

    @DeleteMapping("/users/{name}")
    void deleteByName(@PathVariable("name") String name);

    @Slf4j
    @Component
    class ClientFallbackFactory implements FallbackFactory<UsersResourceFeignClient> {
        @Override
        public UsersResourceFeignClient create(Throwable cause) {
            log.error("Hystrix Fallback is activated: {}", cause.getMessage(), cause);
            return new UsersResourceFeignClient() {
                @Override
                public Optional<UserDto> findByName(String name) {
                    log.warn("Using fallback method for findByName with name = {}", name);
                    throw new FallbackException("UsersResourceFeignClientFallback is activated");
                }

                @Override
                public List<UserDto> findAll() {
                    log.warn("Using fallback method for findAll");
                    throw new FallbackException("UsersResourceFeignClientFallback is activated");
                }

                @Override
                public void save(UserDto dto) {
                    log.warn("Using fallback method for save with UserDto = {}", dto);
                    throw new FallbackException("UsersResourceFeignClientFallback is activated");
                }

                @Override
                public void update(String name, UserDto dto) {
                    log.warn("Using fallback method for update with name = {}, UserDto = {}", name, dto);
                    throw new FallbackException("UsersResourceFeignClientFallback is activated");
                }

                @Override
                public void deleteByName(String name) {
                    log.warn("Using fallback method for deleteByName with name = {}", name);
                    throw new FallbackException("UsersResourceFeignClientFallback is activated");
                }
            };
        }
    }
}
