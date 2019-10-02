package com.github.akraskovski.axon.playground.api.core

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

//Commands

data class CreateAdCommand(@TargetAggregateIdentifier val adId: UUID, val adFileUrl: String)
data class ShowAdCommand(@TargetAggregateIdentifier val adId: UUID, val targetUrl: String)
data class ApproveAdCommand(@TargetAggregateIdentifier val adId: UUID)
data class FinishAdCommand(@TargetAggregateIdentifier val adId: UUID)

//Events

data class AdCreatedEvent(val adId: UUID, val adFileUrl: String)
data class AdShowedRequestEvent(val adId: UUID, val adFileUrl: String, val targetUrl: String)
data class AdApprovedEvent(val adId: UUID)
data class AdFinishedEvent(val adId: UUID)

//Command2Event converters as extension functions

fun CreateAdCommand.toEvent() = AdCreatedEvent(adId, adFileUrl)
fun ShowAdCommand.toEvent(adFileUrl: String) = AdShowedRequestEvent(adId, adFileUrl, targetUrl)
fun ApproveAdCommand.toEvent() = AdApprovedEvent(adId)
fun FinishAdCommand.toEvent() = AdFinishedEvent(adId)

//Queries

class FetchAllAdsQuery
data class FetchAdQuery(val adId: UUID)
