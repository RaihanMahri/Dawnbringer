package com.ubaya.dawnbringer.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ubaya.dawnbringer.databinding.FragmentRegisterBinding
import com.ubaya.dawnbringer.model.User
import com.ubaya.dawnbringer.viewmodel.UserViewModel

class Register : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.btnCreateAccount.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val first = binding.etFirstName.text.toString()
            val last = binding.etLastName.text.toString()
            val pass = binding.etPassword.text.toString()
            val repass = binding.etRepeatPassword.text.toString()

            when {
                username.isBlank() -> binding.layoutUsername.error = "Username tidak boleh kosong"
                pass.length < 6 -> binding.layoutPassword.error = "Minimal 6 karakter"
                pass != repass -> binding.layoutRepeatPassword.error = "Password tidak sama"
                else -> {
                    //error
                    binding.layoutUsername.error = null
                    binding.layoutPassword.error = null
                    binding.layoutRepeatPassword.error = null

                    val user = User(username, first, last, pass)
                    viewModel.register(user, repass) { success, message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        if (success) findNavController().popBackStack()
                    }
                }
            }
        }
    }
}
