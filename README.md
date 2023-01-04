<div align="center">

# database-api

![GitHub release (latest by date including pre-releases)](https://img.shields.io/github/v/release/Heliumdioxid/database-api?color=%23178fff&include_prereleases&label=version&style=flat-square)
![GitHub](https://img.shields.io/github/license/Heliumdioxid/database-api?color=%232f3332&style=flat-square)
![GitHub issues](https://img.shields.io/github/issues/Heliumdioxid/database-api?color=%23113782&style=flat-square)
![GitHub pull requests](https://img.shields.io/github/issues-pr/Heliumdioxid/database-api?color=%234458f2&style=flat-square)

a simple and <b>async</b> java-library for mysql and mongodb <b>database-connections</b>

It is an easy-to-use library that (until now) can connect to mysql and mongodb databases.<br>
In addition, interactions with databases are simplified.
Feel free to leave a ⭐ if you appreciate the work.

[getting started](#-installation) •
[report a bug](#%EF%B8%8F-contributing) •
[feature request](#%EF%B8%8F-contributing)

</div>

---

## Table of Contents
- [🧪 Installation](#-installation)
- [📖 Code examples](#-code-examples)
    - [📍 MySQL](#mysql-database-connection)
    - [📍 MongoDB](#mongo-database-connection)
- [⌛ Pending tasks](#-pending-tasks)
- [🗺️ Contributing](#%EF%B8%8F-contributing)
- [☎️ Contact](#%EF%B8%8F-contact)
- [📜 License](#-license)
<!--
- [🗺️ Project structure](#)
-->

---

## 🧪 Installation
You can integrate the database-api into your project with Maven or Gradle.

### Maven
Maven repository:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Maven dependencies:
```xml
<dependency>
    <groupId>com.github.Heliumdioxid.database-api</groupId>
    <artifactId>api</artifactId>
    <version>1.0.0-rc1</version>
</dependency>

<dependency>
    <groupId>com.github.Heliumdioxid.database-api</groupId>
    <artifactId>mysql</artifactId>
    <version>1.0.0-rc1</version>
</dependency>

<dependency>
    <groupId>com.github.Heliumdioxid.database-api</groupId>
    <artifactId>mongo</artifactId>
    <version>1.0.0-rc1</version>
</dependency>
```

### Gradle
Gradle repository:
```groovy
maven {
    name 'jitpack.io'
    url 'https://jitpack.io'
}
```

Gradle dependency:
```groovy
dependencies {
    implementation 'com.github.Heliumdioxid.database-api:api:1.0.0-rc1'
}

dependencies {
    implementation 'com.github.Heliumdioxid.database-api:mysql:1.0.0-rc1'
}

dependencies {
    implementation 'com.github.Heliumdioxid.database-api:mongo:1.0.0-rc1'
}
```

---

## 📖 Code examples
First, you have to create a `ConnectionData`, either for a mongo or mysql connection.
You need to provide a host, username, password, database-name and port (mostly 3306 for mysql and 27017 for mongodb)
```java
ConnectionData connectionData = new ConnectionData("127.0.0.1", "username", "password", "database", 3306);
```

### mysql-database-connection
> 🤖 The connection to mysql-databases should work properly. 
> If there are any errors, it would be a great help to report them.

Moreover, you have to create some properties, which are then passed on during the connection:
```java
MySQLConnectionConfig mySQLConnectionConfig = new MySQLConnectionConfig(connectionData);
mySQLConnectionConfig.applyDefaultHikariConfig() // recommended setting for HikariCP
```
After that, the connection to the database can be established like this:
```java
MySQLDatabaseConnection mySQLDatabaseConnection = new MySQLDatabaseConnection(mySQLConnectionConfig);

MySQLConnectionHandler mySQLConnectionHandler;
// connect and check for a present value in the optional:
mySQLConnectionHandler = mySQLDatabaseConnection.connect().join().get();
// get the MySQLConnectionHandler with this method:
mySQLConnectionHandler = mySQLDatabaseConnection.getConnectionHandler().get();
```
The `MySQLConnectionHandler` can now be used for communicating with the connected mysql-database:
```java
UpdateResult updateResult = mySQLConnectionHandler.executeUpdate("query"); // execute an update

// executes a query and applies the result to a functional interface
boolean included = mySQLConnectionHandler.executeQuery(resultSet -> resultSet.next(), false, "query");
```
The connection can finally be closed by calling this method:
```java
mySQLDatabaseConnection.disconnect();
```

### mongo-database-connection
> 🤖 The connection to mongo-databases was not testet yet. 
> If you have tried it, I would be very grateful if you would contact me.

Moreover, you have to create some properties, which are then passed on during the connection:
```java
MongoConnectionConfig mongoConnectionConfig = new MongoConnectionConfig(connectionData);
mongoConnectionConfig.applyDefaultMongoClientSettings(); // applies default properties like the uri
```
After that, the connection to the database can be established like this:
```java
MongoDatabaseConnection mongoDatabaseConnection = new MongoDatabaseConnection(mongoConnectionConfig);

MongoConnectionHandler mongoConnectionHandler;
// connect and check for a present value in the optional:
mongoConnectionHandler = mongoDatabaseConnection.connect().join().get();
// get the MongoConnectionHandler with this method:
mongoConnectionHandler = mongoDatabaseConnection.getConnectionHandler().get();
```
The `MongoConnectionHandler` can now be used for communicating with the connected mongo-database (few examples):
```java
Optional<MongoCollection<Document>> mongoCollection = mongoConnectionHandler.getCollection("collection").join();
Optional<Document> document = mongoConnectionHandler.getDocument("collection", "fieldName", "value").join();
```
The connection can finally be closed by calling this method:
```java
mongoDatabaseConnection.disconnect();
```
---

## ⌛ Pending tasks
> 🤖 Please suggest features that we should include in the api.
> Read [Contributing](#%EF%B8%8F-contributing) for details.

---

## 🗺️ Contributing
Before submitting issues and pull requests please read the [CONTRIBUTING.md](CONTRIBUTING.md) file.
Please follow these guidelines to make life easier for all of us.

---

## 📜 License
database-api is distributed under the terms of the `MIT License`. A complete version of the license is available in the [LICENSE.md](LICENSE.md) in this repository. Any contribution made to this project will be licensed under the `MIT License`.<br>
All files are Copyright © 2023 Florian H. and all contributors.

---

## ☎️ Contact
You can contact me via Discord: Heliumdioxid#3963

<!--
*** ---
*** 
*** ## 🗺️ Project structure
*** ```
*** ├── api/
*** │   ├── ConnectionHandler.java
*** │   ├── DatabaseConnection.java
*** │   └── data/
*** │   │   └── ConnectionData.java
*** ├── mongo/
*** ├── mysql/
*** └── ...
*** ```
*** 
*** ---
*** 
*** ## ⌛ Pending tasks
*** - [ ] test mongo-database-module
*** - [ ] \(Optional) integrate other databases into the api
*** 
*** ---
*** 
*** 📢🪛🔧🔨⛏️🪓🔩🪨🪵⚙️⚗️🧪🧬🧫🔭💡📍📌📏⌛⏳☁️🧩♻️🎮📒📬💬📖🤖
-->
