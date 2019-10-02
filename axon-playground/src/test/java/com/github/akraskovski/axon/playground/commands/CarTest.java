package com.github.akraskovski.axon.playground.commands;

import com.github.akraskovski.axon.playground.api.core.CarCreatedEvent;
import com.github.akraskovski.axon.playground.api.core.CarRentedEvent;
import com.github.akraskovski.axon.playground.api.core.CarReturnedEvent;
import com.github.akraskovski.axon.playground.api.core.CreateCarCommand;
import com.github.akraskovski.axon.playground.api.core.RentCarCommand;
import com.github.akraskovski.axon.playground.api.core.ReturnCarCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class CarTest {

    private FixtureConfiguration<Car> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Car.class);
    }

    @Test
    public void testCreateCarCommand() {
        var id = UUID.randomUUID();
        var year = Year.now();
        fixture.givenNoPriorActivity()
            .when(new CreateCarCommand(id, "BMW", "5 series", year))
            .expectSuccessfulHandlerExecution()
            .expectEvents(new CarCreatedEvent(id, "BMW", "5 series", year));
    }

    @Test
    public void testRentCarCommand_IfAlreadyRented() {
        var id = UUID.randomUUID();
        var year = Year.now();
        var createdEvent = new CarCreatedEvent(id, "BMW", "5 series", year);
        var rentedEvent = new CarRentedEvent(id);
        fixture.given(createdEvent, rentedEvent)
            .when(new RentCarCommand(id))
            .expectException(IllegalArgumentException.class)
            .expectExceptionMessage("Car is already rented")
            .expectNoEvents();
    }

    @Test
    public void testRentCarCommand_IfNotRentedYet() {
        var id = UUID.randomUUID();
        var year = Year.now();
        var createdEvent = new CarCreatedEvent(id, "BMW", "5 series", year);
        fixture.given(createdEvent)
            .when(new RentCarCommand(id))
            .expectSuccessfulHandlerExecution()
            .expectEvents(new CarRentedEvent(id));
    }

    @Test
    public void testReturnCarCommand_IfNotReturnedYet() {
        var id = UUID.randomUUID();
        var year = Year.now();
        var createdEvent = new CarCreatedEvent(id, "BMW", "5 series", year);
        var rentedEvent = new CarRentedEvent(id);
        fixture.given(createdEvent, rentedEvent)
            .when(new ReturnCarCommand(id, 10))
            .expectSuccessfulHandlerExecution()
            .expectEvents(new CarReturnedEvent(id, 10))
            .expectState(car -> assertTrue(car.isAvailable()));
    }

    @Test
    public void testReturnCarCommand_IfWasReturnedYet() {
        var id = UUID.randomUUID();
        var year = Year.now();
        var createdEvent = new CarCreatedEvent(id, "BMW", "5 series", year);
        fixture.given(createdEvent)
            .when(new ReturnCarCommand(id, 10))
            .expectException(IllegalArgumentException.class)
            .expectExceptionMessage("Car is already returned")
            .expectNoEvents();
    }
}
