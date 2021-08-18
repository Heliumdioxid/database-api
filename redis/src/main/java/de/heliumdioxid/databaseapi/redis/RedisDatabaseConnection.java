package de.heliumdioxid.databaseapi.redis;

import de.heliumdioxid.databaseapi.api.ConnectionHandler;
import de.heliumdioxid.databaseapi.api.DatabaseConnection;
import de.heliumdioxid.databaseapi.redis.config.RedisConnectionConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Getter
@RequiredArgsConstructor
public class RedisDatabaseConnection implements DatabaseConnection {

    private final RedisConnectionConfig redisConnectionConfig;

    @Override
    public CompletableFuture<Optional<ConnectionHandler>> connect() {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> disconnect() {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> isConnected() {
        return null;
    }

    @Override
    public Optional<ConnectionHandler> getConnectionHandler() {
        return Optional.empty();
    }

}
