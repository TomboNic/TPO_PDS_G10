package com.example.tpo.model.observer.events;

import com.example.tpo.model.observer.IDomainEvent;

public class ScrimFinalized implements IDomainEvent {
    private final int scrimId;

    public ScrimFinalized(int scrimId) { this.scrimId = scrimId; }
    public int getScrimId() { return scrimId; }
}
