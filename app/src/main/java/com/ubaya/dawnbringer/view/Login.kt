package com.ubaya.dawnbringer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentLoginBinding
import com.ubaya.dawnbringer.util.SessionManager
import com.ubaya.dawnbringer.viewmodel.UserViewModel

class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        session = SessionManager(requireContext())

        binding.btnSignIn.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.login(username, password) { success, message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                if (success) {
                    session.save(username)
                    findNavController().navigate(R.id.action_login_to_itemBudgetting)
                }
            }
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }
    }
}
