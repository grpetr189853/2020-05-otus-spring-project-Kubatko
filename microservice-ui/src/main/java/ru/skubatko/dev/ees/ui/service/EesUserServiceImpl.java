package ru.skubatko.dev.ees.ui.service;

import ru.skubatko.dev.ees.ui.domain.EesUser;
import ru.skubatko.dev.ees.ui.dto.EesUserDto;
import ru.skubatko.dev.ees.ui.feign.UsersResourceFeignClient;
import ru.skubatko.dev.ees.ui.mappers.EesUserDtoToResourceMapper;
import ru.skubatko.dev.ees.ui.mappers.EesUserToDtoMapper;
import ru.skubatko.dev.ees.ui.mappers.ResourceUserToDtoMapper;
import ru.skubatko.dev.ees.ui.repository.EesUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EesUserServiceImpl implements EesUserService {

    private final EesUserRepository repository;
    private final UsersResourceFeignClient resource;
    private final ResourceUserToDtoMapper resourceToDtoMapper;
    private final EesUserToDtoMapper entityToDtoMapper;
    private final EesUserDtoToResourceMapper dtoToResourceMapper;

    @Override
    @Transactional(readOnly = true)
    public EesUserDto getByName(String name) {
        Optional<EesUser> userOptional = repository.findByName(name);
        if (userOptional.isEmpty()) {
            return null;
        }

        return entityToDtoMapper.map(userOptional.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EesUserDto> findByName(String userName) {
        Optional<EesUser> user = repository.findByName(userName);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(entityToDtoMapper.map(user.get()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EesUserDto> findAll() {
        return resource.findAll().stream().map(resourceToDtoMapper::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(EesUserDto user) {
        repository.save(new EesUser(user.getName()));
    }

    @Override
    @Transactional
    public void update(String name, EesUserDto dto) {
        Optional<EesUser> userOptional = repository.findByName(name);
        if (userOptional.isEmpty()) {
            return;
        }

        EesUser user = userOptional.get();
        repository.save(user);

        resource.save(dtoToResourceMapper.map(dto));
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        Optional<EesUser> userOptional = repository.findByName(name);
        if (userOptional.isEmpty()) {
            return;
        }

        EesUser user = userOptional.get();
        repository.delete(user);
        resource.deleteByName(name);
    }
}
