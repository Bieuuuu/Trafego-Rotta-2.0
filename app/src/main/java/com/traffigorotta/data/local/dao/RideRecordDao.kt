package com.traffigorotta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.traffigorotta.data.local.entity.RideRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RideRecordDao {

    @Query("SELECT * FROM ride_records ORDER BY timestamp DESC")
    fun getAllRidesFlow(): Flow<List<RideRecordEntity>>

    @Query("SELECT * FROM ride_records WHERE journeyId = :journeyId ORDER BY timestamp DESC")
    fun getRidesByJourneyFlow(journeyId: String): Flow<List<RideRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRide(ride: RideRecordEntity)

    @Query("SELECT COUNT(*) FROM ride_records WHERE status = 'COMPLETED'")
    suspend fun getCompletedRidesCount(): Int

    @Query("SELECT SUM(grossValue) FROM ride_records WHERE status = 'COMPLETED'")
    suspend fun getTotalGrossEarnings(): Double?
}
