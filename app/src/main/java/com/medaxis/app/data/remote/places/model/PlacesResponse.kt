package com.medaxis.app.data.remote.places.model

import com.google.gson.annotations.SerializedName

/**
 * Minimal response model for Google Places Nearby Search.
 * Only the fields required for this app are included.
 */
data class PlacesResponse(
    @SerializedName("results") val results: List<PlaceResult> = emptyList(),
    @SerializedName("status") val status: String? = null,
    @SerializedName("next_page_token") val nextPageToken: String? = null
)

data class PlaceResult(
    @SerializedName("place_id") val placeId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("vicinity") val vicinity: String?,
    @SerializedName("geometry") val geometry: Geometry?,
    @SerializedName("opening_hours") val openingHours: OpeningHours?,
    @SerializedName("plus_code") val plusCode: PlusCode?,
    @SerializedName("formatted_phone_number") val phoneNumber: String?
)

data class Geometry(
    @SerializedName("location") val location: LatLng?
)

data class LatLng(
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?
)

data class OpeningHours(
    @SerializedName("open_now") val openNow: Boolean?
)

data class PlusCode(
    @SerializedName("global_code") val globalCode: String?
)
