package com.ubaya.dawnbringer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.dawnbringer.databinding.FragmentItemReportCardBinding
import com.ubaya.dawnbringer.model.Budget
import com.ubaya.dawnbringer.model.BudgetReport

/**
 * Adapter for Report RecyclerView which displays budgets and usage information.
 */
class ReportAdapter(
    private val items: List<BudgetReport>
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(val binding: FragmentItemReportCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemReportCardBinding.inflate(inflater, parent, false)
        return ReportViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvReportBudgetName.text = item.budget.name
            tvReportUsed.text = "IDR %,d".format(item.used)
            tvReportMax.text = "IDR %,d".format(item.budget.amount)
            val remaining = item.budget.amount - item.used
            tvReportSisa.text = "Sisa: IDR %,d".format(remaining)
            val progress = if (item.budget.amount == 0) 0 else (item.used * 100 / item.budget.amount)
            pbReportProgress.progress = progress
        }
    }
}

/**
 * Simple data class representing report item.
 */
