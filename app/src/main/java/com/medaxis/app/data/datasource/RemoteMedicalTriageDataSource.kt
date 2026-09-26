package com.medaxis.app.data.datasource

import com.medaxis.app.data.remote.medical.model.TriageRequest
import com.medaxis.app.data.remote.medical.model.TriageResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Remote implementation that calls the real medical AI triage service via Retrofit.
 * The base URL should be provided via BuildConfig.MEDICAL_API_BASE_URL.
 */
class RemoteMedicalTriageDataSource(
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.MEDICAL_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
) : MedicalTriageDataSource {

    private val service: com.medaxis.app.data.remote.medical.MedicalTriageService =
        retrofit.create(com.medaxis.app.data.remote.medical.MedicalTriageService::class.java)

    override suspend fun getTriage(symptomText: String): TriageResponse {
        val request = TriageRequest(symptomText = symptomText)
        return service.triage(request)
    }
}
