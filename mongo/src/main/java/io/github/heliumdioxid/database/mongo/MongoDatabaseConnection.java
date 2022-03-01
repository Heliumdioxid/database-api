package io.github.heliumdioxid.database.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.github.heliumdioxid.database.api.DatabaseConnection;
import io.github.heliumdioxid.database.mongo.config.MongoConnectionConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Getter
@RequiredArgsConstructor
public class MongoDatabaseConnection implements DatabaseConnection<MongoConnectionHandler> {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoConnectionHandler mongoConnectionHandler;

    private final MongoConnectionConfig mongoConnectionConfig;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<Optional<MongoConnectionHandler>> connect() {
        return isConnected().thenApplyAsync(connected -> {
            if (connected) {
                if (this.mongoConnectionHandler == null)
                    return Optional.empty();

                return Optional.of(this.mongoConnectionHandler);
            }

            this.mongoClient = MongoClients.create(this.mongoConnectionConfig.getMongoClientSettings());
            this.mongoDatabase = mongoClient.getDatabase(this.mongoConnectionConfig.getConnectionData().getDatabase());

            this.mongoConnectionHandler = new MongoConnectionHandler(this);
            return Optional.of(this.mongoConnectionHandler);
        });
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<Boolean> disconnect() {
        return isConnected().thenApplyAsync(connected -> {
            if (!connected)
                return true;

            this.mongoClient.close();
            this.mongoClient = null;
            return true;
        });
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<Boolean> isConnected() {
        return CompletableFuture.supplyAsync(() -> this.mongoClient != null && this.mongoDatabase != null);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<MongoConnectionHandler> getConnectionHandler() {
        if (this.mongoConnectionHandler == null)
            return Optional.empty();

        return Optional.of(this.mongoConnectionHandler);
    }

}
