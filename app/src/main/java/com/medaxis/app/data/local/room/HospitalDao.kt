package com.medaxis.app.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO for accessing cached hospital entities.
 */
@Dao
interface HospitalDao {
    @Query("SELECT * FROM hospital ORDER BY last_updated DESC")
    fun getAll(): Flow<List<HospitalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(entities: List<HospitalEntity>)

    @Query("DELETE FROM hospital WHERE place_id NOT IN (SELECT place_id FROM hospital ORDER BY last_updated DESC LIMIT :max)")
    suspend fun purgeOld(max: Int = 500)

    /**
     * Synchronous version used for fallback when network fails.
     * Returns a snapshot list of cached hospitals.
     */
    @Query("SELECT * FROM hospital ORDER BY last_updated DESC")
    suspend fun getAllSync(): List<HospitalEntity>
}
