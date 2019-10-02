package com.github.akraskovski.axon.playground.api.rest.rtb

import com.github.akraskovski.axon.playground.api.core.ApproveAdCommand
import com.github.akraskovski.axon.playground.api.core.CreateAdCommand
import com.github.akraskovski.axon.playground.api.core.FinishAdCommand
import com.github.akraskovski.axon.playground.api.core.ShowAdCommand
import com.github.akraskovski.axon.playground.api.rest.requests.CreateAdRequest
import lombok.extern.slf4j.Slf4j
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.util.*

@Slf4j
@RequestMapping("rtb")
@RestController
class AdCommandsController(private val commandGateway: CommandGateway) {

    @PostMapping
    fun create(@RequestBody request: CreateAdRequest) =
            commandGateway.send<Any>(CreateAdCommand(UUID.randomUUID(), request.adFileUrl))!!

    @PostMapping("{id}/show")
    fun show(@PathVariable id: UUID, @RequestParam targetUrl: String) =
            commandGateway.send<Any>(ShowAdCommand(id, targetUrl))!!

    @PostMapping("{id}/approve")
    fun approve(@PathVariable id: UUID) = commandGateway.send<Any>(ApproveAdCommand(id))!!

    @PostMapping("{id}/finish")
    fun finish(@PathVariable id: UUID) = commandGateway.send<Any>(FinishAdCommand(id))!!
}