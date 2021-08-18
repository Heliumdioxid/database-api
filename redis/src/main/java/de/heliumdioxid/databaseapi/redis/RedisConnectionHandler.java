package de.heliumdioxid.databaseapi.redis;

import de.heliumdioxid.databaseapi.api.ConnectionHandler;

public class RedisConnectionHandler extends ConnectionHandler<RedisDatabaseConnection> {

    protected RedisConnectionHandler(RedisDatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

}
