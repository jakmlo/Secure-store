package com.example.projekt_bam

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardInfoViewModel (application: Application) :AndroidViewModel(application) {

    val allCardInfo : LiveData<List<CardInfo>>
    val repository : CardInfoRepository

    init {
        val dao = CardInfoDatabase.getDatabase(application).getCardInfoDao()
        repository = CardInfoRepository(dao)
        allCardInfo = repository.getCardInfoByEmail(Firebase.auth.currentUser?.email!!)
    }

    fun deleteCardInfo (cardInfo: CardInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(cardInfo)
    }

    fun updateCardInfo(cardInfo: CardInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(cardInfo)
    }

    fun addCardInfo(cardInfo: CardInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(cardInfo)
    }
}