package com.example.tpo.model.observer.events;

import com.example.tpo.model.observer.IDomainEvent;

public class LobbyCompleted implements IDomainEvent {
    private final int scrimId;

    public LobbyCompleted(int scrimId) { this.scrimId = scrimId; }
    public int getScrimId() { return scrimId; }
}
