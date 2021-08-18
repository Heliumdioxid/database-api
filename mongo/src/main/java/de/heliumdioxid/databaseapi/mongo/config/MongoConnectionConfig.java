package de.heliumdioxid.databaseapi.mongo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import de.heliumdioxid.databaseapi.api.data.ConnectionData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MongoConnectionConfig {

    private MongoClientSettings mongoClientSettings;
    private final ConnectionData connectionData;

    /**
     * Gets new {@link MongoClientSettings} based on given {@link ConnectionData} with active ssh values
     * @return {@link MongoClientSettings} containing recommended properties and connection-information
     */
    public MongoClientSettings applyDefaultMongoClientSettings() {
         this.mongoClientSettings = MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb://" + this.connectionData.getUsername() + ":" + this.connectionData.getPassword() + "@" +
                this.connectionData.getHost() + ":" + this.connectionData.getPort() + "/" + this.connectionData.getDatabase() + "?ssl=true&sslInvalidHostNameAllowed=true")).build();
         return this.mongoClientSettings;
    }

}
