package com.vibecoding.baitap11.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vibecoding.baitap11.data.DatabaseHelper
import com.vibecoding.baitap11.model.User

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    
    val loginResult = MutableLiveData<User?>()
    val navigateToRegister = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun onLoginClicked() {
        val userStr = username.value
        val passStr = password.value

        if (userStr.isNullOrEmpty() || passStr.isNullOrEmpty()) {
            message.value = "Please enter username and password"
            return
        }

        val user = dbHelper.checkUser(userStr, passStr)
        if (user != null) {
            loginResult.value = user
        } else {
            message.value = "Invalid credentials"
        }
    }

    fun onRegisterClicked() {
        navigateToRegister.value = true
    }
}