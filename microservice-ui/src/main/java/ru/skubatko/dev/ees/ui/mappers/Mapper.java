package ru.skubatko.dev.ees.ui.mappers;

@FunctionalInterface
public interface Mapper<T, R> {

    R map(T t);
}
