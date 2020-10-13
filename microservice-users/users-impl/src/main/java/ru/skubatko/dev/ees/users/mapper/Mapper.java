package ru.skubatko.dev.ees.users.mapper;

@FunctionalInterface
public interface Mapper<T, R> {

    R map(T t);
}
