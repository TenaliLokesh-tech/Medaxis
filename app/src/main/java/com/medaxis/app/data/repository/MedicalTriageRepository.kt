package com.medaxis.app.data.repository

import com.medaxis.app.domain.model.TriageResponse
import com.medaxis.app.data.datasource.MedicalTriageDataSource

/**
 * Repository abstraction for medical triage.
 * Chooses between remote and demo data sources based on BuildConfig.USE_DEMO_MODE.
 */
class MedicalTriageRepository(
    private val remoteDataSource: MedicalTriageDataSource,
    private val demoDataSource: MedicalTriageDataSource
) {
    suspend fun getTriage(symptomText: String): TriageResponse {
        return if (BuildConfig.USE_DEMO_MODE) {
            demoDataSource.getTriage(symptomText)
        } else {
            remoteDataSource.getTriage(symptomText)
        }
    }
}
