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

class Budgetting : Fragment() {
    private lateinit var binding: FragmentBudgettingBinding
    private val viewModel: BudgetViewModel by viewModels()
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
            binding.recyclerBudget.adapter = BudgetAdapter(it) { budget ->
                showDialog(budget, username)
            }
        }

        binding.fabAddBudget.setOnClickListener {
            showDialog(null, username)
        }

        viewModel.fetchBudgets(username) // ✅ Ganti dengan fungsi yang benar
    }

    private fun showDialog(budget: Budget?, username: String) {
        val dialog = DialogAddBudgetFragment(budget = budget, username = username) {
            viewModel.fetchBudgets(username)
        }
        dialog.show(childFragmentManager, "AddBudget")
    }
}
