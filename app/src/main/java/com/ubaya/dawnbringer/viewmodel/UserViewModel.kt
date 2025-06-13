package com.ubaya.dawnbringer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ubaya.dawnbringer.model.User
import com.ubaya.dawnbringer.model.UserDatabase
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = UserDatabase.getInstance(application).userDao()

    fun register(user: User, repeatPass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            if (user.password != repeatPass) {
                onResult(false, "Password tidak sama")
                return@launch
            }

            val exists = dao.getByUsername(user.username)
            if (exists != null) {
                onResult(false, "Username sudah digunakan")
                return@launch
            }

            dao.insert(user)
            onResult(true, "Akun berhasil dibuat")
        }
    }

    fun login(username: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val user = dao.login(username, password)
            if (user != null) {
                onResult(true, "Login berhasil")
            } else {
                onResult(false, "Username atau password salah")
            }
        }
    }
}
