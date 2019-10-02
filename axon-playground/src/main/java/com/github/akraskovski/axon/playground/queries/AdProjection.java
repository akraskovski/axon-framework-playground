package com.github.akraskovski.axon.playground.queries;

import com.github.akraskovski.axon.playground.api.core.AdApprovedEvent;
import com.github.akraskovski.axon.playground.api.core.AdShowedRequestEvent;
import com.github.akraskovski.axon.playground.api.core.FetchAdQuery;
import com.github.akraskovski.axon.playground.api.core.FetchAllAdsQuery;
import com.github.akraskovski.axon.playground.queries.domain.model.AdSummary;
import com.github.akraskovski.axon.playground.queries.domain.model.AdSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdProjection {

    private final AdSummaryRepository adSummaryRepository;

    @EventHandler
    public void on(AdShowedRequestEvent event) {
        var ad = new AdSummary(event.getAdId(), event.getAdFileUrl(), event.getTargetUrl(), false);
        adSummaryRepository.save(ad);
    }

    @EventHandler
    public void on(AdApprovedEvent event) {
        adSummaryRepository.findById(event.getAdId()).ifPresent(AdSummary::startServing);
    }

    @QueryHandler
    public List<AdSummary> on(FetchAllAdsQuery query) {
        return adSummaryRepository.findAll();
    }

    @QueryHandler
    public AdSummary on(FetchAdQuery query) {
        return adSummaryRepository.findById(query.getAdId()).orElseThrow();
    }
}
