package ru.skubatko.dev.ees.criteria.mapper;

@FunctionalInterface
public interface Mapper<T, R> {

    R map(T t);
}
