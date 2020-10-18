package ru.skubatko.dev.ees.users.mappers;

@FunctionalInterface
public interface Mapper<T, R> {

    R map(T t);
}
