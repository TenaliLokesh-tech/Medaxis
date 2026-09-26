package com.medaxis.app.data.repository

import com.medaxis.app.domain.model.Hospital

/** Repository for fetching nearby healthcare facilities */
interface HealthcareRepository {
    /**
     * Retrieves a list of hospitals near the given location. If [specialty] is provided, it is used as a keyword
     * in the Places search to prioritize relevant facilities.
     */
    suspend fun getNearbyHospitals(
        latitude: Double,
        longitude: Double,
        specialty: String?
    ): List<Hospital>
}
