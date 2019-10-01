package com.github.akraskovski.axon.playground.api.core

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.Year
import java.util.*

//Commands
data class CreateCarCommand(
        @TargetAggregateIdentifier val carId: UUID,
        val brand: String,
        val model: String,
        val year: Year
)

data class RentCarCommand(@TargetAggregateIdentifier val carId: UUID)
data class ReturnCarCommand(@TargetAggregateIdentifier val carId: UUID, val distance: Long)

//Events
data class CarCreatedEvent(
        val carId: UUID,
        val brand: String,
        val model: String,
        val year: Year
)

data class CarRentedEvent(val carId: UUID)
data class CarReturnedEvent(val carId: UUID, val distance: Long)

//Queries
class CarAvailableQuery

data class CarFetchQuery(val carId: UUID)
