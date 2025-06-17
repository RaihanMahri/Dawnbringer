package com.ubaya.dawnbringer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.dawnbringer.databinding.FragmentReportBinding
import com.ubaya.dawnbringer.model.Budget
import com.ubaya.dawnbringer.model.BudgetReport
import com.ubaya.dawnbringer.util.SessionManager
import com.ubaya.dawnbringer.viewmodel.BudgetViewModel
import com.ubaya.dawnbringer.viewmodel.ExpenseViewModel
class Report : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private val budgetViewModel: BudgetViewModel by viewModels()
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private lateinit var session: SessionManager
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        session = SessionManager(requireContext())
        username = session.get()
        binding.rvReport.layoutManager = LinearLayoutManager(requireContext())
        username?.let { user ->
            budgetViewModel.budgets.observe(viewLifecycleOwner) { budgets ->
                loadReport(budgets, user)
            }
            budgetViewModel.fetchBudgets(user)
        }

    }
    private fun loadReport(budgets: List<Budget>, user: String) {
        if (budgets.isEmpty()) {
            binding.rvReport.adapter = ReportAdapter(emptyList())
            binding.tvTotalReport.text = "Total Expenses / Budget: Rp 0 / Rp 0"
            return
        }
        val items = mutableListOf<BudgetReport>()
        var totalExpense = 0
        var totalBudget = 0
        var remaining = budgets.size
        budgets.forEach { budget ->
            totalBudget += budget.amount
            expenseViewModel.getTotalByBudget(budget.id, user) { used ->
                items.add(BudgetReport(budget, used))
                totalExpense += used
                remaining -= 1
                if (remaining == 0) {
                    binding.rvReport.adapter = ReportAdapter(items)
                    binding.tvTotalReport.text = "Total Expenses / Budget: " +"Rp %,d / Rp %,d".format(totalExpense, totalBudget)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}



