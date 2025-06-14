package com.ubaya.dawnbringer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ubaya.dawnbringer.model.User
import com.ubaya.dawnbringer.model.UserDatabase
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = UserDatabase.getInstance(application).userDao()

    // Ini adalah repository sederhana langsung menggunakan DAO
    fun getByUsername(username: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            callback(userDao.getByUsername(username))
        }
    }

    fun insert(user: User) {
        viewModelScope.launch {
            userDao.insert(user)
        }
    }

    fun login(username: String, password: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val user = userDao.login(username, password)
            if (user != null) {
                callback(true, "Login berhasil")
            } else {
                callback(false, "Username atau password salah")
            }
        }
    }

    fun updatePassword(username: String, newPassword: String, callback: () -> Unit) {
        viewModelScope.launch {
            val user = userDao.getByUsername(username)
            if (user != null) {
                val updatedUser = user.copy(password = newPassword)
                userDao.insert(updatedUser)
                callback()
            }
        }
    }
    fun register(user: User, repeatPassword: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            if (user.password != repeatPassword) {
                callback(false, "Password tidak cocok")
                return@launch
            }

            val existingUser = userDao.getByUsername(user.username)
            if (existingUser != null) {
                callback(false, "Username sudah digunakan")
                return@launch
            }

            userDao.insert(user)
            callback(true, "Registrasi berhasil")
        }
    }


}
