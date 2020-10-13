package ru.skubatko.dev.ees.ui.mapper;

@FunctionalInterface
public interface Mapper<T, R> {

    R map(T t);
}
