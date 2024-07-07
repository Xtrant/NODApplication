package com.dicoding.nodapplication.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.nodapplication.R
import com.dicoding.nodapplication.databinding.ActivityRegisterBinding
import com.dicoding.nodapplication.result.Result
import com.dicoding.nodapplication.utils.showLoading
import com.dicoding.nodapplication.utils.showToast
import com.dicoding.nodapplication.view.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeViewModel()

        binding.btnRegiter.setOnClickListener {
            register()
        }

        supportActionBar?.hide()
    }

    private fun register() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.register(email, password)
        } else {
            showToast(this, "Please fill in all required fields")
        }
    }

    private fun observeViewModel() {
        viewModel.resultMessage.observe(this) { result ->
            if (result != null)
                when (result) {
                    is Result.Loading -> {
                        showLoading(true, binding.progressIndicator)
                    }

                    is Result.Success -> {
                        showLoading(false, binding.progressIndicator)
                        val message = result.data.message
                        if (message != null) {
                            showToast(this, result.data.message)
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            result.data.error?.let { showToast(this, it) }
                        }
                    }

                    is Result.Error -> {
                        showToast(this, result.error)
                        showLoading(false, binding.progressIndicator)
                    }
                }
        }
    }
}