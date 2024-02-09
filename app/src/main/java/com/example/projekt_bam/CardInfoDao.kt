package com.example.projekt_bam

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCardInfo(cardInfo: CardInfo)

    @Update
    suspend fun updateCardInfo(cardInfo: CardInfo)

    @Delete
    suspend fun deleteCardInfo(cardInfo: CardInfo)

    @Query("SELECT * FROM CardInfo WHERE email = :email")
    fun getCardInfo(email: String): LiveData<List<CardInfo>>
}