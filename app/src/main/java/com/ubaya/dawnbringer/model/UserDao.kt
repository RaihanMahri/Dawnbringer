package com.ubaya.dawnbringer.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getByUsername(username: String): User?

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?
}

