package com.example.projekt_bam

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardInfo(
    val email: String,
    val bankName: String,
    val cardNumber: String?,
) {
    @PrimaryKey(autoGenerate = true) var id = 0
}
