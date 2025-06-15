package com.ubaya.dawnbringer.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ubaya.dawnbringer.databinding.FragmentDialogAddBudgetBinding
import com.ubaya.dawnbringer.model.Budget
import com.ubaya.dawnbringer.viewmodel.BudgetViewModel

class DialogAddBudgetFragment(
    private val budget: Budget? = null,
    private val totalExpense: Int = 0,
    private val onSave: () -> Unit
) : DialogFragment() {

    private lateinit var binding: FragmentDialogAddBudgetBinding
    private val viewModel: BudgetViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogAddBudgetBinding.inflate(LayoutInflater.from(context))

        // Kalau edit mode
        if (budget != null) {
            binding.txtBudgetName.setText(budget.name)
            binding.txtAmount.setText(budget.amount.toString())
            binding.btnSaveBudget.text = "UPDATE"
        }

        binding.btnSaveBudget.setOnClickListener {
            val name = binding.txtBudgetName.text.toString().trim()
            val nominal = binding.txtAmount.text.toString().toIntOrNull() ?: -1

            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nominal < 0) {
                Toast.makeText(context, "Nominal tidak boleh negatif", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (budget != null && nominal < totalExpense) {
                Toast.makeText(
                    context,
                    "Nominal tidak boleh kurang dari total expense: Rp$totalExpense",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val newBudget = budget?.copy(name = name, amount = nominal)
                ?: Budget(name = name, amount = nominal)

            viewModel.addOrUpdateBudget(newBudget, budget != null, totalExpense) { success, msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                if (success) {
                    onSave()
                    dismiss()
                }
            }
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }
}
