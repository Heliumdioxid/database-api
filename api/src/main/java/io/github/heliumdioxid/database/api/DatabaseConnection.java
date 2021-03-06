package io.github.heliumdioxid.database.api;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/** Represents a connection to a database */
public interface DatabaseConnection<E extends ConnectionHandler> {

    /**
     * Connects to a database asynchronously
     * @return optional of {@link ConnectionHandler} of the database-connection
     */
    CompletableFuture<Optional<E>> connect();

    /**
     * Disconnects from a database asynchronously
     * @return boolean that shows whether the database-connection could be closed
     */
    CompletableFuture<Boolean> disconnect();

    /**
     * Checks asynchronously if there is an active mysql-database-connection
     * @return boolean that shows whether an active database-connection exists
     */
    CompletableFuture<Boolean> isConnected();

    /**
     * Gets the {@link ConnectionHandler} of an active database-connection
     * @return {@link ConnectionHandler} of an active database-connection
     */
    Optional<E> getConnectionHandler();

}
