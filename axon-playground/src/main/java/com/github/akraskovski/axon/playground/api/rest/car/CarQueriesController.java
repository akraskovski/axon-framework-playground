package com.github.akraskovski.axon.playground.api.rest.car;

import com.github.akraskovski.axon.playground.api.core.CarAvailableQuery;
import com.github.akraskovski.axon.playground.api.core.CarFetchQuery;
import com.github.akraskovski.axon.playground.queries.domain.model.CarSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf;
import static org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf;

@Slf4j
@RestController
@RequestMapping("car")
@RequiredArgsConstructor
public class CarQueriesController {

    private final QueryGateway queryGateway;

    @GetMapping("{id}")
    public CompletableFuture<CarSummary> fetchByAggregateId(@PathVariable UUID id) {
        return queryGateway.query(new CarFetchQuery(id), instanceOf(CarSummary.class));
    }

    @GetMapping
    public CompletableFuture<List<CarSummary>> fetchAll() {
        return queryGateway.query(new CarAvailableQuery(), multipleInstancesOf(CarSummary.class));
    }
}
