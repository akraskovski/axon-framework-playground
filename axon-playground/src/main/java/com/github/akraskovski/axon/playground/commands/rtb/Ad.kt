package com.github.akraskovski.axon.playground.commands.rtb

import com.github.akraskovski.axon.playground.api.core.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
class Ad(@field:AggregateIdentifier var adId: UUID?) {
    constructor() : this(null)

    lateinit var adFileUrl: String

    @CommandHandler
    constructor(command: CreateAdCommand) : this() {
        AggregateLifecycle.apply(command.toEvent())
    }

    @CommandHandler
    fun handle(command: ShowAdCommand) {
        AggregateLifecycle.apply(command.toEvent(adFileUrl))
    }

    @CommandHandler
    fun handle(command: ApproveAdCommand) {
        AggregateLifecycle.apply(command.toEvent())
    }

    @EventSourcingHandler
    private fun on(event: AdCreatedEvent) {
        adId = event.adId
        adFileUrl = event.adFileUrl
    }
}