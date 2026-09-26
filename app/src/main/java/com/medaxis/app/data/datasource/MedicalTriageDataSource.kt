package com.medaxis.app.data.datasource

/**
 * Abstraction for a medical triage data source.
 * Implementations can be remote (calls an LLM API) or demo (returns static data).
 */
interface MedicalTriageDataSource {
    /**
     * Sends a symptom description to the backend and receives a structured triage response.
     */
    suspend fun getTriage(symptomText: String): com.medaxis.app.data.remote.medical.model.TriageResponse
}
