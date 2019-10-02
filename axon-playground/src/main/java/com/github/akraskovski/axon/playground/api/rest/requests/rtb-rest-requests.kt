package com.github.akraskovski.axon.playground.api.rest.requests

import javax.validation.constraints.NotBlank

class CreateAdRequest(@field:NotBlank var adFileUrl: String) {
    constructor() : this("")
}