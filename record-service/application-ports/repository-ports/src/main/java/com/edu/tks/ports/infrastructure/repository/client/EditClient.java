package com.edu.tks.ports.infrastructure.repository.client;

import com.edu.tks.client.Client;

public interface EditClient {
    void updateActive(String id, boolean active);
    void remove(String id);
    void add(Client client);
}
