package com.ubaya.dawnbringer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.dawnbringer.databinding.FragmentBudgettingBinding
import com.ubaya.dawnbringer.model.Budget
import com.ubaya.dawnbringer.util.SessionManager
import com.ubaya.dawnbringer.viewmodel.BudgetViewModel
import com.ubaya.dawnbringer.viewmodel.ExpenseViewModel
import androidx.navigation.fragment.findNavController
import com.ubaya.dawnbringer.R

class Budgetting : Fragment() {
    private lateinit var binding: FragmentBudgettingBinding
    private val viewModel: BudgetViewModel by viewModels()
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private lateinit var session: SessionManager
    private var budgets = listOf<Budget>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        session = SessionManager(requireContext())
        val username = session.get() ?: return

        binding.recyclerBudget.layoutManager = LinearLayoutManager(requireContext())

        viewModel.budgets.observe(viewLifecycleOwner) {
            budgets = it
            binding.recyclerBudget.adapter = BudgetAdapter(
                it,
                onClick = { budget ->
                    session.saveBudgetId(budget.id)
                    findNavController().navigate(R.id.action_itemBudgetting_to_itemExpense)
                },
                onEdit = { budget ->
                    // Ambil total pengeluaran dulu
                    expenseViewModel.getTotalByBudget(budget.id, username) { totalUsed ->
                        val dialog = DialogEditBudgetFragment(
                            budget = budget,
                            totalExpense = totalUsed
                        ) {
                            viewModel.fetchBudgets(username)
                        }
                        dialog.show(childFragmentManager, "EditBudget")
                    }
                }
            )
        }

        binding.fabAddBudget.setOnClickListener {
            val dialog = DialogAddBudgetFragment(
                budget = null,
                username = username
            ) {
                viewModel.fetchBudgets(username)
            }
            dialog.show(childFragmentManager, "AddBudget")
        }

        viewModel.fetchBudgets(username)
    }
}
