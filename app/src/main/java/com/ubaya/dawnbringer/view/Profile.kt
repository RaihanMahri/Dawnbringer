package com.ubaya.dawnbringer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ubaya.dawnbringer.databinding.FragmentProfileBinding
import com.ubaya.dawnbringer.util.SessionManager
import com.ubaya.dawnbringer.viewmodel.UserViewModel

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        session = SessionManager(requireContext())

        val username = session.getUsername()

        binding.btnChangePassword.setOnClickListener {
            val oldPassword = binding.etOldPassword.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()

            if (newPassword != repeatPassword) {
                Toast.makeText(context, "New password and repeat password do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val username = session.get()
            if (username != null) {
                viewModel.getByUsername(username) { user ->
                    if (user != null && user.password == oldPassword) {
                        viewModel.updatePassword(username, newPassword) {
                            Toast.makeText(context, "Password updated successfully", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Old password incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnSignOut.setOnClickListener {
            session.clear()
            findNavController().navigate(com.ubaya.dawnbringer.R.id.action_itemProfile_to_login)
        }
    }
}
