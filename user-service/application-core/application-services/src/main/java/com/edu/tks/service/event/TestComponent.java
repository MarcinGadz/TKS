package com.edu.tks.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestComponent {

    MessageSerializer serializer = new MessageSerializer();
    MessageDeSerializer deSerializer = new MessageDeSerializer();

    @PostConstruct
    private void test() {
        MessageEntity m = new MessageEntity("TEST");
        byte[] ser = serializer.serialize("test", m);
        MessageEntity e = deSerializer.deserialize("test", ser);
        assert(m.getContent().equals(e.getContent()));
    }

    @Autowired
    private StreamBridge streamBridge;

    @Scheduled(cron = "*/1 * * * * *")
    public void sendMessage(){
        streamBridge.send("producer-out-0",new MessageEntity(" jack from Stream bridge"));
    }
}
