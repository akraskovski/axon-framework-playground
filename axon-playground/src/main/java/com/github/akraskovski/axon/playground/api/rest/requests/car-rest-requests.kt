package com.github.akraskovski.axon.playground.api.rest.requests

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CreateCarRequest(
        @field:NotBlank var brand: String? = null,
        @field:NotBlank var model: String? = null,
        @field:NotNull @field:Min(2010) @field:Max(2019) var year: Int? = null
)