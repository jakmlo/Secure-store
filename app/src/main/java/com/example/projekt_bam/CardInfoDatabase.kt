package com.example.projekt_bam

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CardInfo::class],
    version = 1,
    exportSchema = false
)

abstract class CardInfoDatabase: RoomDatabase() {
    abstract fun getCardInfoDao(): CardInfoDao

    companion object {
        @Volatile
        private var INSTANCE: CardInfoDatabase? = null

        fun getDatabase(context: Context): CardInfoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardInfoDatabase::class.java,
                    "user_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}