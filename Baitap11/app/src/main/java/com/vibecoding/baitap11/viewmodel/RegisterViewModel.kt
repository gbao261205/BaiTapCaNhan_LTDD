package com.vibecoding.baitap11.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vibecoding.baitap11.data.DatabaseHelper
import com.vibecoding.baitap11.model.User

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    val registrationResult = MutableLiveData<Boolean>()
    val navigateToLogin = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun onRegisterClicked() {
        val userStr = username.value
        val passStr = password.value
        val confirmPassStr = confirmPassword.value

        if (userStr.isNullOrEmpty() || passStr.isNullOrEmpty() || confirmPassStr.isNullOrEmpty()) {
            message.value = "Please fill all fields"
            return
        }

        if (passStr != confirmPassStr) {
            message.value = "Passwords do not match"
            return
        }

        if (dbHelper.checkUser(userStr)) {
            message.value = "Username already exists"
            return
        }

        val newUser = User(username = userStr, password = passStr)
        val result = dbHelper.addUser(newUser)

        if (result > -1) {
            message.value = "Registration successful"
            registrationResult.value = true
        } else {
            message.value = "Registration failed"
        }
    }

    fun onLoginClicked() {
        navigateToLogin.value = true
    }
}