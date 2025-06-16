package com.ubaya.dawnbringer.model

import androidx.room.*

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM Expense WHERE budgetId = :budgetId AND username = :username ORDER BY date DESC")
    suspend fun getAllByBudgetAndUsername(budgetId: Int, username: String): List<Expense>

    @Query("SELECT * FROM Expense WHERE id = :id")
    suspend fun getById(id: Int): Expense?

    @Query("SELECT * FROM Expense WHERE username = :username ORDER BY date DESC")
    suspend fun getAllByUsername(username: String): List<Expense>

    @Query("SELECT SUM(nominal) FROM Expense WHERE budgetId = :budgetId AND username = :username")
    suspend fun getTotalByBudget(budgetId: Int, username: String): Int?

}
