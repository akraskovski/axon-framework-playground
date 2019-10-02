package com.github.akraskovski.axon.playground.queries;

import com.github.akraskovski.axon.playground.api.core.AdApprovedEvent;
import com.github.akraskovski.axon.playground.api.core.AdFinishedEvent;
import com.github.akraskovski.axon.playground.queries.domain.model.AdSummary;
import com.github.akraskovski.axon.playground.queries.domain.model.AdSummaryRepository;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import javax.inject.Inject;

@Saga
public class AdServingSaga {

    @Inject
    private transient AdSummaryRepository adSummaryRepository;

    @StartSaga
    @SagaEventHandler(associationProperty = "adId")
    public void on(AdApprovedEvent event) {
        adSummaryRepository.findById(event.getAdId()).ifPresent(AdSummary::startServing);
    }

    @SagaEventHandler(associationProperty = "adId")
    public void on(AdFinishedEvent event) {
        adSummaryRepository.findById(event.getAdId()).ifPresent(AdSummary::stopServing);
        SagaLifecycle.end();
    }
}
