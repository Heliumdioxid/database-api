### database-api
a simple libary for mysql, mongodb and redis database-connections

<br>

Maven:
```xml
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub</name>
        <url>https://maven.pkg.github.com/Heliumdioxid/database-api</url>
        <releases><enabled>true</enabled></releases>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>de.heliumdioxid</groupId>
        <artifactId>database-api</artifactId>
        <version>1.0-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

<br>

Examples:
```java
ConnectionData connectionData = new ConnectionData("127.0.0.1", "username", "password", "database", 3306);
MySQLConnectionConfig mySQLConnectionConfig = new MySQLConnectionConfig(connectionData).applyDefaultHikariConfig();
MySQLDatabaseConnection mySQLDatabaseConnection = new MySQLDatabaseConnection(mySQLConnectionConfig);

<Optional<MySQLConnectionHandler>> optionalMySQLConnectionHandler = mySQLDatabaseConnection.connect().join();
MySQLConnectionHandler mySQLConnectionHandler = mySQLDatabaseConnection.getConnectionHandler();

UpdateResult updateResult = mySQLConnectionHandler.executeUpdate("query");
boolean included = mySQLConnectionHandler.executeQuery(resultSet -> resultSet.next(), false, "query");

mySQLDatabaseConnection.disconnect();
```

```java
ConnectionData connectionData = new ConnectionData("127.0.0.1", "username", "password", "database", 27017);
MongoConnectionConfig mongoConnectionConfig = new MongoConnectionConfig(connectionData).applyDefaultMongoClientSettings();
MongoDatabaseConnection mongoDatabaseConnection = new MongoDatabaseConnection(mongoConnectionConfig);

<Optional<MongoConnectionHandler>> optionalMongoConnectionHandler = mongoDatabaseConnection.connect().join();
MongoConnectionHandler mongoConnectionHandler = mongoDatabaseConnection.getConnectionHandler();

<Optional<MongoConnectionHandler>> optionalDocument = mongoConnectionHandler.getDocument("collection", "fieldName", "value").join();

mongoDatabaseConnection.disconnect();
```