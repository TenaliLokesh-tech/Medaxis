package com.medaxis.app.data.repository

import com.medaxis.app.data.remote.places.PlacesApiService
import com.medaxis.app.data.remote.places.model.PlacesResponse
import com.medaxis.app.data.local.room.HospitalEntity
import com.medaxis.app.data.local.room.HospitalDao
import com.medaxis.app.domain.model.Hospital
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Real implementation of [HealthcareRepository] that calls Google Places and caches results in Room.
 */
class HealthcareRepositoryImpl @Inject constructor(
    private val placesService: PlacesApiService,
    private val hospitalDao: HospitalDao
) : HealthcareRepository {

    override suspend fun getNearbyHospitals(
        latitude: Double,
        longitude: Double,
        specialty: String?
    ): List<Hospital> = withContext(Dispatchers.IO) {
        // Build location string "lat,lng"
        val location = "${latitude},${longitude}"
        // Use specialty as keyword if provided, otherwise generic "hospital"
        val keyword = specialty?.takeIf { it.isNotBlank() }
        try {
            val response: PlacesResponse = placesService.nearbySearch(
                location = location,
                radius = 5000,
                type = "hospital",
                keyword = keyword
            )
            // Map results to Hospital domain model and cache them
            val hospitals = response.results.mapNotNull { result ->
                val placeId = result.placeId ?: return@mapNotNull null
                val name = result.name ?: "Unknown"
                val address = result.vicinity ?: ""
                val lat = result.geometry?.location?.lat ?: latitude
                val lng = result.geometry?.location?.lng ?: longitude
                val isOpen = result.openingHours?.openNow ?: false
                val specialtyStr = specialty ?: "General"
                // Distance calculation (simple placeholder: we will not compute actual distance here)
                val distance = "N/A"
                // Insert or replace into Room cache
                val entity = HospitalEntity(
                    placeId = placeId,
                    name = name,
                    latitude = lat,
                    longitude = lng,
                    address = address,
                    phone = result.phoneNumber,
                    category = "hospital",
                    specialty = specialtyStr,
                    lastUpdated = System.currentTimeMillis()
                )
                hospitalDao.upsertAll(listOf(entity))
                Hospital(
                    id = placeId,
                    name = name,
                    distance = distance,
                    address = address,
                    specialty = specialtyStr,
                    isOpen = isOpen
                )
            }
            // Ensure cache size limit
            hospitalDao.purgeOld(max = 500)
            hospitals
        } catch (e: Exception) {
            // On failure, return cached facilities (if any)
            val cached = hospitalDao.getAllSync()
            cached.map { entity ->
                Hospital(
                    id = entity.placeId,
                    name = entity.name,
                    distance = "N/A",
                    address = entity.address,
                    specialty = entity.specialty ?: "General",
                    isOpen = true // assume open if unknown
                )
            }
        }
    }
}
