package com.edu.tks.service.client;

import com.edu.tks.client.Client;
import com.edu.tks.ports.infrastructure.repository.client.EditClient;
import com.edu.tks.ports.infrastructure.repository.client.ReadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.function.Consumer;
import java.util.logging.Logger;

@Service
public class ClientService {

    private final static String CHANNEL_OUT = "producer-out-0";

    private final static Logger logger = Logger.getLogger(ClientService.class.getName());

    @Autowired
    private StreamBridge streamBridge;
    @Autowired
    private ReadClient readClient;
    @Autowired
    private EditClient editClient;

    @Bean
    public Consumer<ClientMessage> consumer() {
        return message -> {
           logger.info("Received: " + message);
            switch (message.type()) {
                case CREATE -> addClient(new Client(message.id(), false));
                case REMOVE -> removeClient(message.id());
                case ACTIVATE -> updateActive(message.id(), true);
                case DEACTIVATE -> updateActive(message.id(), false);
            }
        };
    }

    public Client getById(String id) {
        return readClient.getById(id);
    }

    public void updateActive(String id, boolean active) {
        editClient.updateActive(id, active);
    }

    public void addClient(Client client) {
        try {
            editClient.add(client);
            if(new Random().nextInt() % 2 == 0) {
                throw new RuntimeException();
            }
            streamBridge.send(CHANNEL_OUT, new ClientMessage(client.id(), EventType.CREATE));
        } catch (Exception ex) {
            streamBridge.send(CHANNEL_OUT, new ClientMessage(client.id(), EventType.CANCEL_CREATE));
        }
    }

    public void removeClient(String id) {
        editClient.remove(id);
    }

}
