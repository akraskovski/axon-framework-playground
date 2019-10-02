package com.github.akraskovski.axon.playground.api.rest.rtb

import com.github.akraskovski.axon.playground.api.core.FetchAllAdsQuery
import com.github.akraskovski.axon.playground.queries.domain.model.AdSummary
import lombok.extern.slf4j.Slf4j
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@Slf4j
@RequestMapping("rtb")
@RestController
class AdQueryController(private val queryGateway: QueryGateway) {

    @GetMapping
    fun fetchAll(): CompletableFuture<MutableList<AdSummary>> =
            queryGateway.query(FetchAllAdsQuery(), ResponseTypes.multipleInstancesOf(AdSummary::class.java))
}