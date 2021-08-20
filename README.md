### database-api
simple and async java-library for mysql and mongodb database-connections

<br>

#### Maven:
```xml
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub Packages Heliumdioxid</name>
        <url>https://maven.pkg.github.com/Heliumdioxid/database-api</url>
    </repository>
</repositories>

<!-- mysql-database-connections -->
<dependencies>
    <dependency>
        <groupId>de.heliumdioxid</groupId>
        <artifactId>mysql</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>

<!-- mongo-database-connections -->
<dependencies>
    <dependency>
        <groupId>de.heliumdioxid</groupId>
        <artifactId>mongo</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

<br>

#### Examples:

```java
// connect to a mysql-database by given properties (data and configuration)
ConnectionData connectionData = new ConnectionData("127.0.0.1", "username", "password", "database", 3306); // provide necessary credentials

MySQLConnectionConfig mySQLConnectionConfig = new MySQLConnectionConfig(connectionData);
mySQLConnectionConfig.applyDefaultHikariConfig() // recommended setting for HikariCP

MySQLDatabaseConnection mySQLDatabaseConnection = new MySQLDatabaseConnection(mySQLConnectionConfig);

MySQLConnectionHandler mySQLConnectionHandler;
mySQLConnectionHandler = mySQLDatabaseConnection.connect().join().get(); // connect - check if a value is present in the optional
mySQLConnectionHandler = mySQLDatabaseConnection.getConnectionHandler().get(); // or get the MySQLConnectionHandler with this method

// use the MySQLConnectionHandler for communicating with the mysql-database
UpdateResult updateResult = mySQLConnectionHandler.executeUpdate("query"); // execute an update
boolean included = mySQLConnectionHandler.executeQuery(resultSet -> resultSet.next(), false, "query"); // executes a query and applies the result to a functional interface

// close mysql-database-connection
mySQLDatabaseConnection.disconnect();
```

```java
// Connect to a mongo-database by given properties (data and configuration)
ConnectionData connectionData = new ConnectionData("127.0.0.1", "username", "password", "database", 27017); // provide necessary credentials

MongoConnectionConfig mongoConnectionConfig = new MongoConnectionConfig(connectionData);
mongoConnectionConfig.applyDefaultMongoClientSettings(); // applies default properties like the uri

MongoDatabaseConnection mongoDatabaseConnection = new MongoDatabaseConnection(mongoConnectionConfig);

MongoConnectionHandler mongoConnectionHandler;
mongoConnectionHandler = mongoDatabaseConnection.connect().join().get(); // connect - check if a value is present in the optional
mongoConnectionHandler = mongoDatabaseConnection.getConnectionHandler().get(); // or get the MongoConnectionHandler with this method

// use the MongoConnectionHandler for communicating with the mongo-database (few examples)
Optional<MongoCollection<Document>> mongoCollection = mongoConnectionHandler.getCollection("collection").join();
Optional<Document> document = mongoConnectionHandler.getDocument("collection", "fieldName", "value").join();

// close mongo-database-connection
mongoDatabaseConnection.disconnect();
```