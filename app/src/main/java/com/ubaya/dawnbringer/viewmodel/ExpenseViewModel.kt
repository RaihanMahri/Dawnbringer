package com.ubaya.dawnbringer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.dawnbringer.model.Expense
import com.ubaya.dawnbringer.model.UserDatabase
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = UserDatabase.getInstance(application).expenseDao()
    val expenses = MutableLiveData<List<Expense>>()

    // Ambil semua pengeluaran untuk budget tertentu berdasarkan username
    fun loadExpenses(budgetId: Int, username: String) {
        viewModelScope.launch {
            val result = dao.getAllByBudgetAndUsername(budgetId, username)
            expenses.postValue(result)
        }
    }

    // Tambah data expense
    fun insert(expense: Expense, onSuccess: () -> Unit) {
        viewModelScope.launch {
            dao.insert(expense)
            onSuccess()
        }
    }

    // Hapus data expense
    fun delete(expense: Expense, onSuccess: () -> Unit) {
        viewModelScope.launch {
            dao.delete(expense)
            onSuccess()
        }
    }

    // Dapatkan detail expense berdasarkan ID
    fun getExpenseById(id: Int, callback: (Expense?) -> Unit) {
        viewModelScope.launch {
            val result = dao.getById(id)
            callback(result)
        }
    }

    // Hitung total pengeluaran untuk budget dan username tertentu
    fun getTotalByBudget(budgetId: Int, username: String, callback: (Int) -> Unit) {
        viewModelScope.launch {
            val total = dao.getTotalByBudget(budgetId, username) ?: 0
            callback(total)
        }
    }

}
