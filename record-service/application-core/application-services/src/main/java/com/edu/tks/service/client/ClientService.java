package com.edu.tks.service.client;

import com.edu.tks.client.Client;
import com.edu.tks.ports.infrastructure.repository.client.EditClient;
import com.edu.tks.ports.infrastructure.repository.client.ReadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.logging.Logger;

@Service
public class ClientService {

    @Bean
    public Consumer<ClientMessage> consumer() {
        return message -> {
            Logger.getLogger(getClass().getName()).warning("received " + message);
            switch (message.type()) {
                case CREATE -> addClient(new Client(message.id(), false));
                case REMOVE -> removeClient(message.id());
                case ACTIVATE -> updateActive(message.id(), true);
                case DEACTIVATE -> updateActive(message.id(), false);
            }
        };
    }


    @Autowired
    private ReadClient readClient;

    @Autowired
    private EditClient editClient;
    public Client getById(String id) {
        return readClient.getById(id);
    }

    public void updateActive(String id, boolean active) {
        editClient.updateActive(id, active);
    }

    public void addClient(Client client) {
        editClient.add(client);
    }

    public void removeClient(String id) {
        editClient.remove(id);
    }

//    @Autowired
//    private StreamBridge streamBridge;
//
//    private static final String CHANNEL_IN = "consumer-in-0";
//    private static final String CHANNEL_OUT = "producer-out-0";


}
