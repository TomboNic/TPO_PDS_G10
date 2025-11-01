package com.example.tpo.model.observer.events;

import com.example.tpo.model.observer.IDomainEvent;

public class ScrimStateChanged implements IDomainEvent {
    private final String from;
    private final String to;
    private final int scrimId;

    public ScrimStateChanged(String from, String to, int scrimId) {
        this.from = from;
        this.to = to;
        this.scrimId = scrimId;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public int getScrimId() { return scrimId; }
}

