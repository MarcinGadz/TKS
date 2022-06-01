package com.edu.tks.service.event;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Component
public class NumberPublisher {

    @Bean
    public Supplier<MessageEntity> producer() {
        return () -> new MessageEntity(" jack from Streams");
    }

    @Bean
    public Consumer<MessageEntity> consumer() {
        return message -> Logger.getLogger(getClass().getName()).warning("received " + message);
    }
}
