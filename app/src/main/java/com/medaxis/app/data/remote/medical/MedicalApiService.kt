package com.medaxis.app.data.remote.medical

import com.medaxis.app.data.remote.medical.model.TriageRequest
import com.medaxis.app.data.remote.medical.model.TriageResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/** Retrofit service for the external medical AI triage API */
interface MedicalApiService {
    @Headers("Content-Type: application/json")
    @POST("triage")
    suspend fun getTriage(@Body request: TriageRequest): TriageResponse
}
