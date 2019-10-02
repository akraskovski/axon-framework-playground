package com.github.akraskovski.axon.playground.api.rest.rtb

import com.github.akraskovski.axon.playground.api.core.CreateAdCommand
import com.github.akraskovski.axon.playground.api.rest.requests.CreateAdRequest
import lombok.extern.slf4j.Slf4j
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Slf4j
@RequestMapping("rtb")
@RestController
class AdController(private val commandGateway: CommandGateway) {

    @PostMapping
    fun create(@RequestBody request: CreateAdRequest) =
            commandGateway.send<Any>(CreateAdCommand(UUID.randomUUID(), request.adFileUrl))!!
}