package com.ubaya.dawnbringer.model

import androidx.room.*

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM Expense WHERE budgetId = :budgetId")
    suspend fun getByBudget(budgetId: Int): List<Expense>
}
