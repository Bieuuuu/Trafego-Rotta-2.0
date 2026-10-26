package com.traffigorotta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.traffigorotta.data.local.entity.FinancialEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FinancialEntryDao {

    @Query("SELECT * FROM financial_entries ORDER BY timestampIso DESC")
    fun getAllEntriesFlow(): Flow<List<FinancialEntryEntity>>

    @Query("SELECT * FROM financial_entries WHERE type = :type ORDER BY timestampIso DESC")
    fun getEntriesByTypeFlow(type: String): Flow<List<FinancialEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: FinancialEntryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<FinancialEntryEntity>)

    @Query("DELETE FROM financial_entries WHERE id = :id")
    suspend fun deleteEntryById(id: String)

    @Query("DELETE FROM financial_entries")
    suspend fun clearAll()
}
