package de.heliumdioxid.databaseapi.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import de.heliumdioxid.databaseapi.api.ConnectionHandler;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Getter
public class MongoConnectionHandler extends ConnectionHandler<MongoDatabaseConnection> {

    private final MongoDatabase mongoDatabase;

    protected MongoConnectionHandler(MongoDatabaseConnection databaseConnection) {
        super(databaseConnection);
        this.mongoDatabase = databaseConnection.getMongoDatabase();
    }

    public CompletableFuture<Optional<MongoCollection<Document>>> getCollection(final String collection) {
        return CompletableFuture.supplyAsync(() -> Optional.of(this.mongoDatabase.getCollection(collection))).exceptionally(throwable -> {
            throwable.printStackTrace();
            return Optional.empty();
        });
    }

    public CompletableFuture<Void> insertDocument(final String collection, final Document document) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> {
            optionalMongoCollection.ifPresent(mongoCollection -> mongoCollection.insertOne(document));
            return null;
        });
    }

    public CompletableFuture<Void> insertDocuments(final String collection, final Document... documents) {
        return insertDocuments(collection, Arrays.asList(documents));
    }

    public CompletableFuture<Void> insertDocuments(final String collection, final List<Document> documents) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> {
            optionalMongoCollection.ifPresent(mongoCollection -> mongoCollection.insertMany(documents));
            return null;
        });
    }

    public CompletableFuture<Optional<UpdateResult>> updateDocument(final String collection, final String fieldName, final Object value, final Bson operation) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.updateOne(Filters.eq(fieldName, value), operation)));
    }

    public CompletableFuture<Optional<UpdateResult>> updateDocuments(final String collection, final String fieldName, final Object value, final Bson operation) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.updateMany(Filters.eq(fieldName, value), operation)));
    }

    public CompletableFuture<Optional<DeleteResult>> deleteDocument(final String collection, final String fieldName, final Object value) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.deleteOne(Filters.eq(fieldName, value))));
    }

    public CompletableFuture<Optional<DeleteResult>> deleteDocuments(final String collection, final String fieldName, final Object value) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.deleteMany(Filters.eq(fieldName, value))));
    }

    public CompletableFuture<Optional<Document>> getDocument(final String collection, final String fieldName, final Object value) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.find(Filters.eq(fieldName, value)).first()));
    }

    public CompletableFuture<Optional<MongoCursor<Document>>> iterator(final String collection, final String fieldName, final Object value) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.find(Filters.eq(fieldName, value)).iterator()));
    }

}
