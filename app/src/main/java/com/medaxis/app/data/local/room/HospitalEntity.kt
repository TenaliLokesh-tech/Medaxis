package com.medaxis.app.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a cached healthcare facility.
 */
@Entity(tableName = "hospital")
data class HospitalEntity(
    @PrimaryKey @ColumnInfo(name = "place_id") val placeId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "specialty") val specialty: String?,
    @ColumnInfo(name = "last_updated") val lastUpdated: Long
)
