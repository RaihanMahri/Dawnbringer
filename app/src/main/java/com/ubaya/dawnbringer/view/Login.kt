package com.ubaya.dawnbringer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import android.content.Intent
import com.ubaya.dawnbringer.R
import com.ubaya.dawnbringer.databinding.FragmentLoginBinding
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
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(username, password) { success, message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                if (success) {
                    session.save(username)

                    val dest = findNavController().currentDestination?.id
                    if (dest == R.id.login2) {
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        findNavController().navigate(R.id.action_login_to_itemExpense)
                    }
                }
            }
        }

        binding.btnSignUp.setOnClickListener {
            val dest = findNavController().currentDestination?.id
            if (dest == R.id.login2) {
                findNavController().navigate(R.id.action_login2_to_register2)
            } else {
                findNavController().navigate(R.id.action_login_to_register)
            }
        }
    }
}