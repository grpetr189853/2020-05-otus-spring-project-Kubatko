package ru.skubatko.dev.ees.ui.service;

import ru.skubatko.dev.ees.ui.domain.EesUserDetails;
import ru.skubatko.dev.ees.ui.feign.UsersResourceFeignClient;
import ru.skubatko.dev.ees.ui.feign.dto.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EesUserDetailsService implements UserDetailsService {

    private final UsersResourceFeignClient usersResource;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserDto> user = usersResource.findByName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException(userName + " not found"));
        return user.map(EesUserDetails::new).get();
    }
}
