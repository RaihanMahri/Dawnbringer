package com.ubaya.dawnbringer.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ubaya.dawnbringer.databinding.FragmentDialogExpenseDetailBinding
import com.ubaya.dawnbringer.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

class DialogExpenseDetailFragment(private val expenseId: Int) : DialogFragment() {
    private lateinit var binding: FragmentDialogExpenseDetailBinding
    private val viewModel: ExpenseViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogExpenseDetailBinding.inflate(LayoutInflater.from(context))

        viewModel.getExpenseById(expenseId) { expense ->
            expense?.let {
                binding.txtNote.text = it.note
                binding.txtNominal.text = "Rp %,d".format(it.nominal)

                val date = Date(it.date * 1000)
                val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("id", "ID"))
                binding.txtDate.text = formatter.format(date)
            }
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }
}
