package com.ubaya.dawnbringer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.dawnbringer.model.Budget
import com.ubaya.dawnbringer.model.UserDatabase
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = UserDatabase.getInstance(application).budgetDao()
    val budgets = MutableLiveData<List<Budget>>()

    // Ambil semua budget berdasarkan username
    fun fetchBudgets(username: String) {
        viewModelScope.launch {
            budgets.postValue(dao.getAllByUsername(username)) // Ini oke jika DAO-nya `suspend`
        }
    }


    // Tambah atau update budget
    fun addOrUpdateBudget(budget: Budget, isEdit: Boolean, totalExpense: Int, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            if (isEdit) {
                if (budget.amount < totalExpense) {
                    callback(false, "Nominal tidak boleh kurang dari total pengeluaran")
                    return@launch
                }
                dao.update(budget)
                callback(true, "Budget berhasil diperbarui")
            } else {
                dao.insert(budget)
                callback(true, "Budget berhasil ditambahkan")
            }
            // Refresh list setelah perubahan
            fetchBudgets(budget.username)
        }
    }

    fun delete(budget: Budget) {
        viewModelScope.launch {
            dao.delete(budget)
            fetchBudgets(budget.username)
        }
    }

    fun getBudgetById(id: Int, callback: (Budget?) -> Unit) {
        viewModelScope.launch {
            callback(dao.getById(id))
        }
    }
}
