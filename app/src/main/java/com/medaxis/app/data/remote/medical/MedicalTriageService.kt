package com.medaxis.app.data.remote.medical

import com.medaxis.app.data.remote.medical.model.TriageRequest
import com.medaxis.app.data.remote.medical.model.TriageResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/** Retrofit service for medical AI triage */
interface MedicalTriageService {
    @Headers("Content-Type: application/json")
    @POST("triage")
    suspend fun triage(@Body request: TriageRequest): TriageResponse
}
