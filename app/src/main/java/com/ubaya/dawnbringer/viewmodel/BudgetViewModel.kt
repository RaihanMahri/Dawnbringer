package com.ubaya.dawnbringer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.dawnbringer.model.Budget
import com.ubaya.dawnbringer.model.BudgetDatabase
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {
    private val budgetDao = BudgetDatabase.getInstance(application).budgetDao()

    private val _budgets = MutableLiveData<List<Budget>>()
    val budgets: LiveData<List<Budget>> = _budgets

    fun loadBudgets() {
        viewModelScope.launch {
            _budgets.postValue(budgetDao.getAll())
        }
    }

    fun addOrUpdateBudget(budget: Budget, isEdit: Boolean, totalExpense: Int, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            if (isEdit && budget.amount < totalExpense) {
                callback(false, "Nominal tidak boleh kurang dari total expense: Rp$totalExpense")
                return@launch
            }
            budgetDao.insert(budget)
            loadBudgets()
            callback(true, "Budget disimpan")
        }
    }
}
