package com.ubaya.dawnbringer.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ubaya.dawnbringer.databinding.FragmentDialogEditBudgetBinding
import com.ubaya.dawnbringer.model.Budget
import com.ubaya.dawnbringer.viewmodel.BudgetViewModel

class DialogEditBudgetFragment(
    private val budget: Budget,
    private val totalExpense: Int,
    private val onUpdate: () -> Unit
) : DialogFragment() {

    private lateinit var binding: FragmentDialogEditBudgetBinding
    private val viewModel: BudgetViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogEditBudgetBinding.inflate(LayoutInflater.from(context))

        //pre-fill data
        binding.txtBudgetName.setText(budget.name)
        binding.txtAmount.setText(budget.amount.toString())
        binding.btnUpdateBudget.text = "UPDATE"

        binding.btnUpdateBudget.setOnClickListener {
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

            if (nominal < totalExpense) {
                Toast.makeText(
                    context,
                    "Nominal tidak boleh kurang dari total pengeluaran: Rp$totalExpense",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val updatedBudget = budget.copy(name = name, amount = nominal)

            viewModel.addOrUpdateBudget(updatedBudget, true, totalExpense) { success, msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                if (success) {
                    onUpdate()
                    dismiss()
                }
            }
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }
}
