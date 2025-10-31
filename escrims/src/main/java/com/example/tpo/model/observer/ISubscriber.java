package com.example.tpo.model.observer;

public interface ISubscriber {
    void onEvent(IDomainEvent e);
}

