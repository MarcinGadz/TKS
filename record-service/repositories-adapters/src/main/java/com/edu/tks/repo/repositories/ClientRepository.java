package com.edu.tks.repo.repositories;

import com.edu.tks.repo.entity.ClientEntity;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ClientRepository {
    private final List<ClientEntity> clients = new LinkedList<>(Arrays.asList(
            new ClientEntity("cebbee82-2398-4dc2-a94d-a4c863286ff0", true),
            new ClientEntity("bbe979ba-ed9c-4028-8c18-97740c25ae99", false),
            new ClientEntity("c6f3fbf7-135b-498e-8154-3a5b9d291145", true),
            new ClientEntity("f6abb370-5dbb-4473-bf8d-505bdcf6ccce", true),
            new ClientEntity("73d2c12b-179e-4700-879d-8e3de56fe55f", true),
            new ClientEntity("bfac0aaa-e7d2-4e33-9fa9-8111b14bcd58", true)));


    public void updateActive(String id, boolean active) {
        getById(id).setActive(active);
    }

    public void remove(String id) {
        clients.remove(getById(id));
    }

    public void add(ClientEntity c) {
        clients.add(c);
    }

    public ClientEntity getById(String id) {
        return clients.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(RuntimeException::new);
    }
}
