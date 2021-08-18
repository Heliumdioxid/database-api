package de.heliumdioxid.databaseapi.mysql.utils;

@FunctionalInterface
public interface Function<ResultSet, T> {

    T apply(ResultSet resultSet);

}
