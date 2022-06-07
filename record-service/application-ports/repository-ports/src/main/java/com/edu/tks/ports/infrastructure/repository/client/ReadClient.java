package com.edu.tks.ports.infrastructure.repository.client;

import com.edu.tks.client.Client;

public interface ReadClient {
    Client getById(String id);
}
