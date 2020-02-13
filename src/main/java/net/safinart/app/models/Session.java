package net.safinart.app.models;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

public final class Session {
    private String name;
    private String password;
    private ZonedDateTime startedDate;
    private UUID id;

    public Session(String name, String password) {
        this.name = name;
        this.password = password;
        this.startedDate = ZonedDateTime.now(ZoneOffset.UTC);
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ZonedDateTime getStartedDate() {
        return startedDate;
    }
    
    public UUID getId() {
        return id;
    }
}