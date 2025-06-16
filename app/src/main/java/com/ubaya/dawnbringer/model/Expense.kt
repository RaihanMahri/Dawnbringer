package com.ubaya.dawnbringer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val note: String,
    val nominal: Int,
    val date: Long,
    val budgetId: Int,
    val username: String
)

