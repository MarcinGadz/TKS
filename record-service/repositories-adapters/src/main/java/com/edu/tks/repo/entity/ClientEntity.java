package com.edu.tks.repo.entity;

public class ClientEntity {
    private String id;
    private boolean active;

    public ClientEntity(String id, boolean active) {
        this.id = id;
        this.active = active;
    }

    public ClientEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
