package de.heliumdioxid.databaseapi.mysql;

import de.heliumdioxid.databaseapi.api.ConnectionHandler;
import de.heliumdioxid.databaseapi.mysql.utils.Function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class MySQLConnectionHandler extends ConnectionHandler<MySQLDatabaseConnection> {

    private Connection connection;

    protected MySQLConnectionHandler(final MySQLDatabaseConnection databaseConnection) {
        super(databaseConnection);
        databaseConnection.getConnection().join().ifPresent(connection -> this.connection = connection);
    }

    /**
     * Executes a query
     * @param query Query to be executed
     * @param function FunctionalInterface {@link Function}
     * @param defaultValue default return value on failure
     * @return {@param defaultValue} on failure or applied {@link ResultSet}
     */
    public <T> T executeQuery(final Function<ResultSet, T> function, final T defaultValue, final String query) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                return function.apply(resultSet);
            } catch (Exception throwable) {
                return defaultValue;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return defaultValue;
    }

    /**
     * Executes a query
     * @param function FunctionalInterface {@link Function}
     * @param defaultValue default return value on failure
     * @param query Query to be executed
     * @param parameters Parameters for query
     * @return {@param defaultValue} on failure or applied {@link ResultSet}
     */
    public <T> T executeQuery(final Function<ResultSet, T> function, final T defaultValue, final String query, final String... parameters) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {

            final AtomicInteger atomicInteger = new AtomicInteger(1);
            for (String parameter : parameters)
                preparedStatement.setString(atomicInteger.getAndIncrement(), parameter);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                return function.apply(resultSet);

            } catch (Exception throwable) {
                return defaultValue;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return defaultValue;
    }

    /**
     * Executes an update
     * @param query Query to be executed
     * @return {@link UpdateResult} result of the update-execution
     */
    public UpdateResult executeUpdate(final String query) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            final int result = preparedStatement.executeUpdate();
            return Arrays.stream(UpdateResult.values()).filter(updateResult -> updateResult.getResult() == result).findFirst().orElse(UpdateResult.FAILURE);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return UpdateResult.FAILURE;
        }
    }

    /**
     * Executes an update
     * @param query Query to be executed
     * @param parameters Parameters for query
     * @return {@link UpdateResult} result of the update-execution
     */
    public UpdateResult executeUpdate(final String query, final String... parameters) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {

            final AtomicInteger atomicInteger = new AtomicInteger(1);
            for (String parameter : parameters)
                preparedStatement.setString(atomicInteger.getAndIncrement(), parameter);

            final int result = preparedStatement.executeUpdate();
            return Arrays.stream(UpdateResult.values()).filter(updateResult -> updateResult.getResult() == result).findFirst().orElse(UpdateResult.FAILURE);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return UpdateResult.FAILURE;
        }
    }

    /** Enumeration representing the result of an execution of an update */
    public enum UpdateResult {
        SUCCESS(1),
        SUCCESS_EMPTY(2),
        FAILURE(-1);

        final int result;

        UpdateResult(final int result) {
            this.result = result;
        }

        /**
         * Gets the result as an integer (result of {@link PreparedStatement} update execution)
         * @return result in the form of an integer
         */
        public int getResult() {
            return result;
        }
    }

}
