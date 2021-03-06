package io.github.heliumdioxid.database.api.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ConnectionData {

    private final String host, username, password, database;
    private final int port;

}
