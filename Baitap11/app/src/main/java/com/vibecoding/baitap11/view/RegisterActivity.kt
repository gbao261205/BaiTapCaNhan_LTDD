package com.vibecoding.baitap11.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vibecoding.baitap11.R
import com.vibecoding.baitap11.databinding.ActivityRegisterBinding
import com.vibecoding.baitap11.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.registrationResult.observe(this) { success ->
            if (success) {
                // Navigate back to login or stay here?
                // For now, let's go back to LoginActivity automatically
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        viewModel.navigateToLogin.observe(this) { navigate ->
            if (navigate) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        viewModel.message.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}