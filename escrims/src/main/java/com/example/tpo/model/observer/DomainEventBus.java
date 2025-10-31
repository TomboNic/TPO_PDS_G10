package com.example.tpo.model.observer;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus {
    private final List<ISubscriber> subs = new ArrayList<>();

    public void subscribe(ISubscriber s) { subs.add(s); }
    public void publish(IDomainEvent e) { for (ISubscriber s : subs) s.onEvent(e); }
}

