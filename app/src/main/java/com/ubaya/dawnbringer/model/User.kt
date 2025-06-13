package com.ubaya.dawnbringer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val username: String, // Contoh: username sebagai primary key
    val firstName: String,
    val lastName: String,
    val password: String
)
