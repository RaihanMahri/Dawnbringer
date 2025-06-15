package com.ubaya.dawnbringer.model

import androidx.room.*

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: Budget)

    @Update
    suspend fun update(budget: Budget)

    @Query("SELECT * FROM Budget")
    suspend fun getAll(): List<Budget>

    @Query("SELECT * FROM budget WHERE username = :username")
    suspend fun getAllByUsername(username: String): List<Budget>


    @Delete
    suspend fun delete(budget: Budget)

    @Query("SELECT * FROM Budget WHERE id = :id")
    suspend fun getById(id: Int): Budget?


}
