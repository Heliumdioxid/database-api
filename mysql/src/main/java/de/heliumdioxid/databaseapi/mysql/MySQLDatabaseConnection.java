package de.heliumdioxid.databaseapi.mysql;

import com.zaxxer.hikari.HikariDataSource;
import de.heliumdioxid.databaseapi.api.DatabaseConnection;
import de.heliumdioxid.databaseapi.mysql.config.MySQLConnectionConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Getter
@RequiredArgsConstructor
public class MySQLDatabaseConnection implements DatabaseConnection<MySQLConnectionHandler> {

    private HikariDataSource hikariDataSource;
    private MySQLConnectionHandler mySQLConnectionHandler;

    private final MySQLConnectionConfig mySQLConnectionConfig;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<Optional<MySQLConnectionHandler>> connect() {
        return isConnected().thenApplyAsync(connected -> {
            if (connected) {
                if (this.mySQLConnectionHandler == null)
                    return Optional.empty();

                return Optional.of(this.mySQLConnectionHandler);
            }

            this.hikariDataSource = new HikariDataSource(this.mySQLConnectionConfig.getHikariConfig());

            this.mySQLConnectionHandler = new MySQLConnectionHandler(this);
            return Optional.of(this.mySQLConnectionHandler);
        });
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<Boolean> disconnect() {
        return isConnected().thenApplyAsync(connected -> {
            if (!connected)
                return true;

            this.hikariDataSource.close();
            return this.hikariDataSource.isClosed();
        });
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<Boolean> isConnected() {
        return CompletableFuture.supplyAsync(() -> this.hikariDataSource != null && !this.hikariDataSource.isClosed() && !this.hikariDataSource.isRunning());
    }

    /** {@inheritDoc} */
    @Override
    public Optional<MySQLConnectionHandler> getConnectionHandler() {
        if (this.mySQLConnectionHandler == null)
            return Optional.empty();

        return Optional.of(this.mySQLConnectionHandler);
    }

    /** Gets the {@link Connection} of the {@link MySQLDatabaseConnection#getHikariDataSource()}
     * @return optional of the connection
     */
    public CompletableFuture<Optional<Connection>> getConnection() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return Optional.of(this.hikariDataSource.getConnection());
            } catch (SQLException exception) {
                exception.printStackTrace();
                return Optional.empty();
            }
        });
    }

}
