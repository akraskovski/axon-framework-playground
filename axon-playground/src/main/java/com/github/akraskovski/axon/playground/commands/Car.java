package com.github.akraskovski.axon.playground.commands;

import com.github.akraskovski.axon.playground.api.core.CarCreatedEvent;
import com.github.akraskovski.axon.playground.api.core.CarRentedEvent;
import com.github.akraskovski.axon.playground.api.core.CarReturnedEvent;
import com.github.akraskovski.axon.playground.api.core.CreateCarCommand;
import com.github.akraskovski.axon.playground.api.core.RentCarCommand;
import com.github.akraskovski.axon.playground.api.core.ReturnCarCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.common.Assert.isFalse;
import static org.axonframework.common.Assert.isTrue;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * An {@link org.axonframework.modelling.command.AggregateRoot} class defining the car domain model.
 */
@Aggregate
public class Car {

    @AggregateIdentifier
    private UUID carId;
    private boolean available;
    private long mileage;

    public Car() {
    }

    @CommandHandler
    public Car(CreateCarCommand command) {
        apply(new CarCreatedEvent(command.getCarId(), command.getBrand(), command.getModel(), command.getYear()));
    }

    @CommandHandler
    public void handle(RentCarCommand command) {
        isFalse(available, () -> "Car is already rented");
        apply(new CarRentedEvent(carId));
    }

    @CommandHandler
    public void handle(ReturnCarCommand command) {
        isTrue(available, () -> "Car is already returned");
        apply(new CarReturnedEvent(carId, command.getDistance()));
    }

    @EventSourcingHandler
    protected void on(CarCreatedEvent event) {
        carId = event.getCarId();
        available = true;
        mileage = 1;
    }

    @EventSourcingHandler
    protected void on(CarRentedEvent event) {
        available = false;
    }

    @EventSourcingHandler
    protected void on(CarReturnedEvent event) {
        available = true;
        mileage += event.getDistance();
    }
}
