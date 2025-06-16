package com.ubaya.dawnbringer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.dawnbringer.R
import com.ubaya.dawnbringer.databinding.FragmentExpenseTrackBinding
import com.ubaya.dawnbringer.model.Budget
import com.ubaya.dawnbringer.model.Expense
import com.ubaya.dawnbringer.util.SessionManager
import com.ubaya.dawnbringer.viewmodel.BudgetViewModel
import com.ubaya.dawnbringer.viewmodel.ExpenseViewModel

class ExpenseTrack : Fragment() {
    private var _binding: FragmentExpenseTrackBinding? = null
    private val binding get() = _binding!!
    private val budgetViewModel: BudgetViewModel by viewModels()
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private lateinit var session: SessionManager
    private var budgets = listOf<Budget>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentExpenseTrackBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        session = SessionManager(requireContext())
        val username = session.get() ?: return

        binding.recyclerExpense.layoutManager = LinearLayoutManager(requireContext())

        budgetViewModel.budgets.observe(viewLifecycleOwner) { list ->
            budgets = list
            updateAdapter(expenseViewModel.expenses.value ?: emptyList())
        }

        expenseViewModel.expenses.observe(viewLifecycleOwner) { list ->
            updateAdapter(list)
        }

        budgetViewModel.fetchBudgets(username)
        expenseViewModel.loadExpenses(username)

        binding.fabAddExpense.setOnClickListener {
            if (budgets.isEmpty()) return@setOnClickListener
            val dialog = DialogAddExpenseFragment(username, budgets) {
                expenseViewModel.loadExpenses(username)
            }
            dialog.show(childFragmentManager, "addExpense")
        }
    }

    private fun updateAdapter(expenses: List<Expense>) {
        val map = budgets.associateBy({ it.id }, { it.name })
        binding.recyclerExpense.adapter = ExpenseAdapter(
            expenses,
            map,
            onClick = { expense ->
                DialogExpenseDetailFragment(expense.id).show(childFragmentManager, "detail")
            },
            onLongClick = {}

        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
