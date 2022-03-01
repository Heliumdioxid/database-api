package io.github.heliumdioxid.database.mysql.utils;

@FunctionalInterface
public interface Function<ResultSet, T> {

    T apply(ResultSet resultSet);

}
