package com.ubaya.dawnbringer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.dawnbringer.databinding.FragmentItembudgetBinding
import com.ubaya.dawnbringer.model.Budget

class BudgetAdapter(val budgets: List<Budget>, val onEdit: (Budget) -> Unit) :
    RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    inner class BudgetViewHolder(val binding: FragmentItembudgetBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentItembudgetBinding.inflate(inflater, parent, false)
        return BudgetViewHolder(binding)
    }

    override fun getItemCount() = budgets.size

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val item = budgets[position]
        with(holder.binding) {
            txtBudgetName.text = item.name
            txtAmount.text = "Rp %,d".format(item.amount)
            root.setOnClickListener {
                onEdit(item)
            }
        }
    }
}
