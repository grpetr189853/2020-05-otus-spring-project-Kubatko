package ru.skubatko.dev.ees.employers.mapper;

@FunctionalInterface
public interface Mapper<T, R> {

    R map(T t);
}
