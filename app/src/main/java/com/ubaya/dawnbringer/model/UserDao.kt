package com.ubaya.dawnbringer.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE username = :username")
    @RewriteQueriesToDropUnusedColumns
    suspend fun getByUsername(username: String): User?

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    @RewriteQueriesToDropUnusedColumns
    suspend fun login(username: String, password: String): User?
}


