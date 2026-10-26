package com.traffigorotta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.traffigorotta.data.local.entity.JourneyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JourneyDao {

    @Query("SELECT * FROM journeys ORDER BY startTimeIso DESC")
    fun getAllJourneysFlow(): Flow<List<JourneyEntity>>

    @Query("SELECT * FROM journeys WHERE isActive = 1 LIMIT 1")
    fun getActiveJourneyFlow(): Flow<JourneyEntity?>

    @Query("SELECT * FROM journeys WHERE isActive = 0 ORDER BY startTimeIso DESC LIMIT 1")
    suspend fun getLastCompletedJourney(): JourneyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJourney(journey: JourneyEntity)

    @Update
    suspend fun updateJourney(journey: JourneyEntity)

    @Query("UPDATE journeys SET isActive = 0, endTimeIso = :endTimeIso WHERE isActive = 1")
    suspend fun endActiveJourneys(endTimeIso: String)
}
