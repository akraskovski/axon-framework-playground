package com.github.akraskovski.axon.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class AxonPlaygroundApplication {

    private final EventStore eventStore;
    private final CommandBus commandBus;

    public static void main(String[] args) {
        SpringApplication.run(AxonPlaygroundApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Event Store: {}", eventStore);
        log.info("Command Bus: {}", commandBus);
    }

}