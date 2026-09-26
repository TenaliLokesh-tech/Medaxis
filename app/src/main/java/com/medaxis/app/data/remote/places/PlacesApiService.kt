package com.medaxis.app.data.remote.places

import com.medaxis.app.data.remote.places.model.PlacesResponse
import retrofit2.http.GET
import com.medaxis.app.BuildConfig
import retrofit2.http.Query

/** Retrofit service for Google Places Nearby Search */
interface PlacesApiService {
    @GET("maps/api/place/nearbysearch/json")
    suspend fun nearbySearch(
        @Query("location") location: String, // "lat,lng"
        @Query("radius") radius: Int = 5000, // meters
        @Query("type") type: String = "hospital",
        @Query("keyword") keyword: String? = null,
        @Query("key") apiKey: String = BuildConfig.GOOGLE_PLACES_API_KEY
    ): PlacesResponse
}
