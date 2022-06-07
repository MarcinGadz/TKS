package com.edu.tks.ports.aggregates.converters.repository;

import com.edu.tks.client.Client;
import com.edu.tks.repo.entity.ClientEntity;

public class ClientConverter {
    public static ClientEntity convertToEntity(Client c) {
        return new ClientEntity(c.id(), c.active());
    }
    public static Client convertToDomain(ClientEntity c) {
        return new Client(c.getId(), c.isActive());
    }
}
