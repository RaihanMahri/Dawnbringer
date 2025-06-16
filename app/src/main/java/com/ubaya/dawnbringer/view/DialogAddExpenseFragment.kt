package com.ubaya.dawnbringer.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ubaya.dawnbringer.databinding.FragmentDialogAddExpenseBinding
import com.ubaya.dawnbringer.model.Expense
import com.ubaya.dawnbringer.viewmodel.ExpenseViewModel
import java.util.*

class DialogAddExpenseFragment(
    private val username: String,
    private val budgetId: Int,
    private val onSave: () -> Unit
) : DialogFragment() {

    private lateinit var binding: FragmentDialogAddExpenseBinding
    private val viewModel: ExpenseViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogAddExpenseBinding.inflate(LayoutInflater.from(context))

        binding.btnSaveExpense.setOnClickListener {
            val note = binding.txtNote.text.toString().trim()
            val nominal = binding.txtNominal.text.toString().toIntOrNull()

            if (note.isEmpty()) {
                Toast.makeText(context, "Catatan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nominal == null || nominal <= 0) {
                Toast.makeText(context, "Nominal tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val timestamp = System.currentTimeMillis() / 1000

            val expense = Expense(
                note = note,
                nominal = nominal,
                date = timestamp,
                budgetId = budgetId,
                username = username
            )

            viewModel.insert(expense) {
                Toast.makeText(context, "Pengeluaran ditambahkan", Toast.LENGTH_SHORT).show()
                onSave()
                dismiss()
            }

        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }
}
