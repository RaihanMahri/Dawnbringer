package com.ubaya.dawnbringer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.dawnbringer.databinding.FragmentItemExpenseBinding
import com.ubaya.dawnbringer.model.Expense
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(
    val expenses: List<Expense>,
    private val budgetNames: Map<Int, String>,
    val onClick: (Expense) -> Unit,
    val onLongClick: (Expense) -> Unit)
    : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(val binding: FragmentItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemExpenseBinding.inflate(inflater, parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun getItemCount() = expenses.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        with(holder.binding) {
            txtNominal.text = "IDR %,d".format(expense.nominal)
            val date = Date(expense.date * 1000)
            val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("id", "ID"))
            txtDate.text = formatter.format(date)
            chipBudget.text = budgetNames[expense.budgetId] ?: ""
            txtNominal.setOnClickListener { onClick(expense) }
        }
    }


}
