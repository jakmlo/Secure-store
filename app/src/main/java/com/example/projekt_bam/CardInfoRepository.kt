package com.example.projekt_bam

import androidx.lifecycle.LiveData

class CardInfoRepository(private val cardInfoDao: CardInfoDao) {

    fun getCardInfoByEmail(email: String): LiveData<List<CardInfo>> {
        return cardInfoDao.getCardInfo(email)
    }

    suspend fun insert(cardInfo: CardInfo) {
        cardInfoDao.insertCardInfo(cardInfo)
    }

    suspend fun delete(cardInfo: CardInfo){
        cardInfoDao.deleteCardInfo(cardInfo)
    }

    suspend fun update(cardInfo: CardInfo){
        cardInfoDao.updateCardInfo(cardInfo)
    }
}