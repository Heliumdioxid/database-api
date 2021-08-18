package de.heliumdioxid.databaseapi.api;

public abstract class ConnectionHandler<T extends DatabaseConnection> {

    protected final T databaseConnection;

    protected ConnectionHandler(final T databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

}
