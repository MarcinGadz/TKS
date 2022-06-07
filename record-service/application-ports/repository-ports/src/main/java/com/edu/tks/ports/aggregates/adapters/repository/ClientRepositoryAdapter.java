package com.edu.tks.ports.aggregates.adapters.repository;

import com.edu.tks.client.Client;
import com.edu.tks.ports.aggregates.converters.repository.ClientConverter;
import com.edu.tks.ports.infrastructure.repository.client.EditClient;
import com.edu.tks.ports.infrastructure.repository.client.ReadClient;
import com.edu.tks.repo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientRepositoryAdapter implements EditClient, ReadClient {

    @Autowired
    private ClientRepository repo;

    @Override
    public void updateActive(String id, boolean active) {
        repo.updateActive(id, active);
    }

    @Override
    public void remove(String id) {
        repo.remove(id);
    }

    @Override
    public void add(Client client) {
        repo.add(ClientConverter.convertToEntity(client));
    }

    @Override
    public Client getById(String id) {
        return ClientConverter.convertToDomain(repo.getById(id));
    }
}
