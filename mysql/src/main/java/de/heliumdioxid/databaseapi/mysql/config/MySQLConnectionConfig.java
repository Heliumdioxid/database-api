package de.heliumdioxid.databaseapi.mysql.config;

import com.zaxxer.hikari.HikariConfig;
import de.heliumdioxid.databaseapi.api.data.ConnectionData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MySQLConnectionConfig {

    private HikariConfig hikariConfig;
    private final ConnectionData connectionData;

    /**
     * Creates a new instance of the MySQLConnectionConfig containing a {@link HikariConfig}
     * @param connectionData {@link ConnectionData} information for database-connection
     */
    public MySQLConnectionConfig(final ConnectionData connectionData) {
        this.hikariConfig = new HikariConfig();
        this.connectionData = connectionData;
    }

    /**
     * Gets a new {@link HikariConfig} based on given {@link ConnectionData} with values from https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
     * @return {@link HikariConfig} containing recommended properties and connection-information
     */
    public HikariConfig applyDefaultHikariConfig() {
        this.hikariConfig = new HikariConfig();

        this.hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        this.hikariConfig.setJdbcUrl("jdbc:mysql://" + this.connectionData.getHost() + ":" + this.connectionData.getPort() + "/" + this.connectionData.getDatabase() + "?serverTimezone=UTC");
        this.hikariConfig.setUsername(connectionData.getUsername());
        this.hikariConfig.setPassword(connectionData.getPassword());

        this.hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        this.hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        this.hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        this.hikariConfig.addDataSourceProperty("useServerPrepStmts", true);
        this.hikariConfig.addDataSourceProperty("useLocalSessionState", true);
        this.hikariConfig.addDataSourceProperty("rewriteBatchedStatements", true);
        this.hikariConfig.addDataSourceProperty("cacheResultSetMetadata", true);
        this.hikariConfig.addDataSourceProperty("cacheServerConfiguration", true);
        this.hikariConfig.addDataSourceProperty("elideSetAutoCommits", true);
        this.hikariConfig.addDataSourceProperty("maintainTimeStats", false);

        return this.hikariConfig;
    }

}
