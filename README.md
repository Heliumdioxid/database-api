### database-api
a simple and async java-library for mysql and mongodb database-connections

<br>

#### Maven:
```xml
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub</name>
        <url>https://maven.pkg.github.com/Heliumdioxid/database-api</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>de.heliumdioxid</groupId>
        <artifactId>database-api</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

<br>

#### Examples:

```java
// connect to a mysql-database by given properties (data and configuration)
ConnectionData connectionData = new ConnectionData("127.0.0.1", "username", "password", "database", 3306); // provide necessary credentials
MySQLConnectionConfig mySQLConnectionConfig = new MySQLConnectionConfig(connectionData).applyDefaultHikariConfig(); // recommanded setting for HikariCP
MySQLDatabaseConnection mySQLDatabaseConnection = new MySQLDatabaseConnection(mySQLConnectionConfig);

<Optional<MySQLConnectionHandler>> optionalMySQLConnectionHandler = mySQLDatabaseConnection.connect().join();
MySQLConnectionHandler mySQLConnectionHandler = mySQLDatabaseConnection.getConnectionHandler();

// use the MySQLConnectionHandler for communicating with the mysql-database
UpdateResult updateResult = mySQLConnectionHandler.executeUpdate("query"); // execute an update
boolean included = mySQLConnectionHandler.executeQuery(resultSet -> resultSet.next(), false, "query"); // executes a query applies the result to a funtional interface
        
// close mysql-database-connection
mySQLDatabaseConnection.disconnect();
```

```java
// Connect to a mongo-database by given properties (data and configuration)
ConnectionData connectionData = new ConnectionData("127.0.0.1", "username", "password", "database", 27017); // provide necessary credentials
MongoConnectionConfig mongoConnectionConfig = new MongoConnectionConfig(connectionData).applyDefaultMongoClientSettings(); // applies default properties like the uri
MongoDatabaseConnection mongoDatabaseConnection = new MongoDatabaseConnection(mongoConnectionConfig);

<Optional<MongoConnectionHandler>> optionalMongoConnectionHandler = mongoDatabaseConnection.connect().join();
MongoConnectionHandler mongoConnectionHandler = mongoDatabaseConnection.getConnectionHandler();

// use the MongoConnectionHandler for communicating with the mongo-database
<Optional<MongoConnectionHandler>> optionalDocument = mongoConnectionHandler.getDocument("collection", "fieldName", "value").join();

// close mongo-database-connection
mongoDatabaseConnection.disconnect();
```