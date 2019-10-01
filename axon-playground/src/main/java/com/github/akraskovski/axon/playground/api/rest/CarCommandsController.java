package com.github.akraskovski.axon.playground.api.rest;

import com.github.akraskovski.axon.playground.api.core.CreateCarCommand;
import com.github.akraskovski.axon.playground.api.core.RentCarCommand;
import com.github.akraskovski.axon.playground.api.core.ReturnCarCommand;
import com.github.akraskovski.axon.playground.api.rest.requests.CreateCarRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Year;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static java.util.UUID.randomUUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("car")
@RestController
public class CarCommandsController {

    private final CommandGateway commandGateway;

    @PostMapping
    public CompletableFuture<String> create(@RequestBody @Valid CreateCarRequest request) {
        var command = new CreateCarCommand(randomUUID(), request.getBrand(), request.getModel(), Year.of(request.getYear()));
        log.info("Sending a command {} to a command gateway", command);
        return commandGateway.send(command);
    }

    @PostMapping("{id}/rent")
    public CompletableFuture<String> rent(@PathVariable UUID id) {
        var command = new RentCarCommand(id);
        log.info("Sending a command {} to a command gateway", command);
        return commandGateway.send(command);
    }

    @PostMapping("{id}/return")
    public CompletableFuture<String> giveBack(@PathVariable UUID id, @RequestParam Long mileage) {
        var command = new ReturnCarCommand(id, mileage);
        log.info("Sending a command {} to a command gateway", command);
        return commandGateway.send(command);
    }
}
