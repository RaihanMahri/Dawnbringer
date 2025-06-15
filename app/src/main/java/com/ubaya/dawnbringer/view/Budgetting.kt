package com.ubaya.dawnbringer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.dawnbringer.databinding.FragmentBudgettingBinding
import com.ubaya.dawnbringer.model.Budget
import com.ubaya.dawnbringer.viewmodel.BudgetViewModel

class Budgetting : Fragment() {
    private lateinit var binding: FragmentBudgettingBinding
    private val viewModel: BudgetViewModel by viewModels()
    private var budgets = listOf<Budget>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerBudget.layoutManager = LinearLayoutManager(requireContext())
        viewModel.budgets.observe(viewLifecycleOwner) {
            budgets = it
            binding.recyclerBudget.adapter = BudgetAdapter(it) { budget ->
                showDialog(budget)
            }
        }

        binding.fabAddBudget.setOnClickListener {
            showDialog(null)
        }

        viewModel.loadBudgets()
    }

    private fun showDialog(budget: Budget?) {
        val dialog = DialogAddBudgetFragment(budget = budget) {
            viewModel.loadBudgets()
        }
        dialog.show(childFragmentManager, "AddBudget")
    }
}
