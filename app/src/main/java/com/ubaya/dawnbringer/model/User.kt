package com.ubaya.dawnbringer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val username: String,
    val firstName: String,
    val lastName: String,
    val password: String
)
