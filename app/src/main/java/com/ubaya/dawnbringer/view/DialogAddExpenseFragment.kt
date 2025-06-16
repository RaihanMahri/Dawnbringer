package com.ubaya.dawnbringer.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ubaya.dawnbringer.databinding.FragmentDialogAddExpenseBinding
import com.ubaya.dawnbringer.model.Budget
import com.ubaya.dawnbringer.model.Expense
import com.ubaya.dawnbringer.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

class DialogAddExpenseFragment(
    private val username: String,
    private val budgets: List<Budget>,
    private val onSave: () -> Unit
) : DialogFragment() {

    private lateinit var binding: FragmentDialogAddExpenseBinding
    private val expenseViewModel: ExpenseViewModel by activityViewModels()
    private var selectedBudget: Budget? = null
    private var usedAmount: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogAddExpenseBinding.inflate(LayoutInflater.from(context))
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
        binding.txtDate.text = dateFormat.format(Date())

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, budgets.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerBudget.adapter = adapter

        binding.spinnerBudget.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedBudget = budgets[position]
                updateProgress()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.btnSaveExpense.setOnClickListener {
            val note = binding.txtNote.text.toString().trim()
            val nominal = binding.txtNominal.text.toString().toIntOrNull()
            val budget = selectedBudget ?: budgets.first()

            if (note.isEmpty()) {
                Toast.makeText(context, "Catatan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nominal == null || nominal < 0) {
                Toast.makeText(context, "Nominal tidak boleh negatif", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nominal > budget.amount - usedAmount) {
                Toast.makeText(context, "Nominal tidak boleh lebih dari budget", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val timestamp = System.currentTimeMillis() / 1000

            val expense = Expense(
                note = note,
                nominal = nominal,
                date = System.currentTimeMillis() / 1000,
                budgetId = budget.id,
                username = username
            )

            expenseViewModel.insert(expense) {
                Toast.makeText(context, "Pengeluaran ditambahkan", Toast.LENGTH_SHORT).show()
                onSave()
                dismiss()
            }
        }
        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }
    private fun updateProgress() {
        val budget = selectedBudget ?: return
        expenseViewModel.getTotalByBudget(budget.id, username) { total ->
            usedAmount = total
            val remaining = budget.amount - total
            val percent = if (budget.amount == 0) 0 else (remaining * 100 / budget.amount)
            binding.progressBudget.progress = percent
        }
    }
}
