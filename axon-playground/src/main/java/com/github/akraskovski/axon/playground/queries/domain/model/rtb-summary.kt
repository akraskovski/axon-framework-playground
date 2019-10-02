package com.github.akraskovski.axon.playground.queries.domain.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class AdSummary(@Id var adId: UUID?, var fileUrl: String, var servingUrl: String, var serving: Boolean) {
    constructor() : this(null, "", "", false)

    fun startServing() {
        serving = true
    }

    fun stopServing() {
        serving = false
    }
}

@Repository
interface AdSummaryRepository : JpaRepository<AdSummary, UUID>