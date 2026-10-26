package com.traffigorotta.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.traffigorotta.data.local.dao.FinancialEntryDao
import com.traffigorotta.data.local.dao.JourneyDao
import com.traffigorotta.data.local.dao.RideRecordDao
import com.traffigorotta.data.local.entity.FinancialEntryEntity
import com.traffigorotta.data.local.entity.JourneyEntity
import com.traffigorotta.data.local.entity.RideRecordEntity

/**
 * Banco de dados local SQLite (Room) do Trafego Rotta.
 *
 * Princípios (AGENTS.md & LGPD):
 * - 100% Offline-first: funciona em túneis, subsolos e garagens sem internet.
 * - Processamento local no aparelho, garantindo privacidade absoluta dos dados.
 */
@Database(
    entities = [
        FinancialEntryEntity::class,
        JourneyEntity::class,
        RideRecordEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RottaDatabase : RoomDatabase() {

    abstract fun financialEntryDao(): FinancialEntryDao
    abstract fun journeyDao(): JourneyDao
    abstract fun rideRecordDao(): RideRecordDao

    companion object {
        @Volatile
        private var INSTANCE: RottaDatabase? = null

        fun getInstance(context: Context): RottaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RottaDatabase::class.java,
                    "rotta_local.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
