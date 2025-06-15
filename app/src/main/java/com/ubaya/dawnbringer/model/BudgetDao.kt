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
}
