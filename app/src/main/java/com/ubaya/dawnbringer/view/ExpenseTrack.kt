package com.ubaya.dawnbringer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ubaya.dawnbringer.R
import com.ubaya.dawnbringer.databinding.FragmentExpenseTrackBinding
import com.ubaya.dawnbringer.util.SessionManager
import com.ubaya.dawnbringer.viewmodel.BudgetViewModel

class ExpenseTrack : Fragment() {
    private var _binding: FragmentExpenseTrackBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BudgetViewModel by viewModels()
    private lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentExpenseTrackBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        session = SessionManager(requireContext())
        val budgetId = session.getBudgetId()
        budgetId?.let { id ->
            viewModel.getBudgetById(id) { budget ->
                binding.tvExpenseTitle.text = "Expense: ${budget?.name ?: id}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
