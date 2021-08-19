package de.heliumdioxid.databaseapi.mongo;

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

    /**
     * Gets a {@link MongoCollection<Document>} asynchronously
     * @param collection name of the collection
     * @return {@link MongoCollection<Document>} of the given collection-name
     */
    public CompletableFuture<Optional<MongoCollection<Document>>> getCollection(final String collection) {
        return CompletableFuture.supplyAsync(() -> Optional.of(this.mongoDatabase.getCollection(collection))).exceptionally(throwable -> {
            throwable.printStackTrace();
            return Optional.empty();
        });
    }

    /**
     * Inserts a {@link Document} asynchronously into a MongoCollection
     * @param collection name of the collection
     */
    public CompletableFuture<Void> insertDocument(final String collection, final Document document) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> {
            optionalMongoCollection.ifPresent(mongoCollection -> mongoCollection.insertOne(document));
            return null;
        });
    }

    /**
     * Inserts a list of {@link Document} asynchronously into a MongoCollection
     * @param collection name of the collection
     */
    public CompletableFuture<Void> insertDocuments(final String collection, final Document... documents) {
        return insertDocuments(collection, Arrays.asList(documents));
    }

    /**
     * Inserts a list of {@link Document} asynchronously into a MongoCollection
     * @param collection name of the collection
     */
    public CompletableFuture<Void> insertDocuments(final String collection, final List<Document> documents) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> {
            optionalMongoCollection.ifPresent(mongoCollection -> mongoCollection.insertMany(documents));
            return null;
        });
    }

    /**
     * Updates a {@link Document} of a MongoCollection asynchronously
     * @param collection name of the collection
     * @param fieldName name of the field in the collection
     * @param value value of the given field in the collection
     * @param operation {@link Bson}
     * @return {@link UpdateResult} of the update
     */
    public CompletableFuture<Optional<UpdateResult>> updateDocument(final String collection, final String fieldName, final Object value, final Bson operation) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.updateOne(Filters.eq(fieldName, value), operation)));
    }

    /**
     * Updates a list of {@link Document} of a MongoCollection asynchronously
     * @param collection name of the collection
     * @param fieldName name of the field in the collection
     * @param value value of the given field in the collection
     * @param operation {@link Bson}
     * @return {@link UpdateResult} of the update
     */
    public CompletableFuture<Optional<UpdateResult>> updateDocuments(final String collection, final String fieldName, final Object value, final Bson operation) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.updateMany(Filters.eq(fieldName, value), operation)));
    }

    /**
     * Deletes a {@link Document} of a MongoCollection asynchronously
     * @param collection name of the collection
     * @param fieldName name of the field in the collection
     * @param value value of the given field in the collection
     * @return {@link DeleteResult} of the deletion
     */
    public CompletableFuture<Optional<DeleteResult>> deleteDocument(final String collection, final String fieldName, final Object value) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.deleteOne(Filters.eq(fieldName, value))));
    }

    /**
     * Deletes a list of {@link Document} of a MongoCollection asynchronously
     * @param collection name of the collection
     * @param fieldName name of the field in the collection
     * @param value value of the given field in the collection
     * @return {@link DeleteResult} of the deletion
     */
    public CompletableFuture<Optional<DeleteResult>> deleteDocuments(final String collection, final String fieldName, final Object value) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.deleteMany(Filters.eq(fieldName, value))));
    }

    /**
     * Gets the fist {@link Document} of a MongoCollection with the given filter asynchronously
     * @param collection name of the collection
     * @param fieldName name of the field in the collection
     * @param value value of the given field in the collection
     * @return fist found {@link Document}
     */
    public CompletableFuture<Optional<Document>> getDocument(final String collection, final String fieldName, final Object value) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.find(Filters.eq(fieldName, value)).first()));
    }

    /**
     * Gets a {@link MongoCursor<Document>} of a MongoCollection with the given filter asynchronously
     * @param collection name of the collection
     * @param fieldName name of the field in the collection
     * @param value value of the given field in the collection
     * @return iterator of all found {@link Document}
     */
    public CompletableFuture<Optional<MongoCursor<Document>>> iterator(final String collection, final String fieldName, final Object value) {
        return getCollection(collection).thenApplyAsync(optionalMongoCollection -> optionalMongoCollection.map(mongoCollection -> mongoCollection.find(Filters.eq(fieldName, value)).iterator()));
    }

}
