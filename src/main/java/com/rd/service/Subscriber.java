package com.rd.service;

import java.util.Arrays;
import java.util.Optional;

public enum Subscriber {

    GITHUB(1, Constants.GITHUB),
    REMOTE_OK(2, Constants.REMOTE_OK),
    STACKOVERFLOW(3, Constants.STACKOVERFLOW);

    private final int id;
    private final String name;

    Subscriber(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Optional<Subscriber> getById(int id) {
        return Arrays.stream(Subscriber.values()).filter(subscriber -> subscriber.id == id).findFirst();
    }
}
