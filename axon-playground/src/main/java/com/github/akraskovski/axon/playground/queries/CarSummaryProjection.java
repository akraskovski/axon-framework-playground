package com.github.akraskovski.axon.playground.queries;

import com.github.akraskovski.axon.playground.api.core.CarAvailableQuery;
import com.github.akraskovski.axon.playground.api.core.CarCreatedEvent;
import com.github.akraskovski.axon.playground.api.core.CarFetchQuery;
import com.github.akraskovski.axon.playground.api.core.CarReturnedEvent;
import com.github.akraskovski.axon.playground.queries.domain.model.CarSummary;
import com.github.akraskovski.axon.playground.queries.domain.model.CarSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CarSummaryProjection {

    private final CarSummaryRepository carSummaryRepository;

    @EventHandler
    public void on(CarCreatedEvent event) {
        carSummaryRepository.save(new CarSummary(event.getCarId(), 1));
    }

    @EventHandler
    public void on(CarReturnedEvent event) {
        carSummaryRepository.findById(event.getCarId())
            .ifPresent(carSummary -> carSummary.increaseMileage(event.getDistance()));
    }

    @QueryHandler
    public CarSummary on(CarFetchQuery query) {
        return carSummaryRepository.findById(query.getCarId()).orElseThrow();
    }

    @QueryHandler
    public List<CarSummary> on(CarAvailableQuery query) {
        return carSummaryRepository.findAll();
    }
}
