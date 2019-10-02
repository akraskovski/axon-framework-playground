package com.github.akraskovski.axon.playground.commands.rtb

import com.github.akraskovski.axon.playground.api.core.AdCreatedEvent
import com.github.akraskovski.axon.playground.api.core.CreateAdCommand
import com.github.akraskovski.axon.playground.api.core.toEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
class Ad(@field:AggregateIdentifier private var adId: UUID?) {
    constructor() : this(null)

    @CommandHandler
    constructor(command: CreateAdCommand) : this() {
        AggregateLifecycle.apply(command.toEvent())
    }

    @EventSourcingHandler
    private fun on(event: AdCreatedEvent) {
        adId = event.adId
    }
}