package de.heliumdioxid.databaseapi.redis.config;

import de.heliumdioxid.databaseapi.api.data.ConnectionData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RedisConnectionConfig {

    private final ConnectionData connectionData;

}
