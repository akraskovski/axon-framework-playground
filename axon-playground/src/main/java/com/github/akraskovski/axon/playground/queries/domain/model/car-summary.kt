package com.github.akraskovski.axon.playground.queries.domain.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CarSummary(@Id var carId: UUID?, var mileage: Long) {
    constructor() : this(null, 0)

    fun increaseMileage(newDistance: Long) {
        mileage += newDistance
    }
}

@Repository
interface CarSummaryRepository : JpaRepository<CarSummary, UUID>