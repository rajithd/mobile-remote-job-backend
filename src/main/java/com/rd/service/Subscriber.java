package com.rd.service;

public enum Subscriber {

    GITHUB(1, "GitHub"),
    STACKOVERFLOW(2, "Stackoverflow");

    private final int id;
    private final String name;

    private Subscriber(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
