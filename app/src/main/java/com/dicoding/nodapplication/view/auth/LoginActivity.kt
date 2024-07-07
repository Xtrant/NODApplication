package com.dicoding.nodapplication.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.nodapplication.R
import com.dicoding.nodapplication.databinding.ActivityLoginBinding
import com.dicoding.nodapplication.result.Result
import com.dicoding.nodapplication.utils.showLoading
import com.dicoding.nodapplication.utils.showToast
import com.dicoding.nodapplication.view.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeViewModel()

        binding.run {

            btnLogin.setOnClickListener {
                login()
            }

            tvMove.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

        }

        supportActionBar?.hide()

    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.login(email, password)
        } else {
            showToast(this, "Please fill in all required fields")
        }
    }

    private fun observeViewModel() {
        viewModel.resultLogin.observe(this) { result ->
            if (result != null)
                when (result) {
                    is Result.Loading -> {
                        showLoading(true, binding.progressIndicator)
                    }

                    is Result.Success -> {
                        showLoading(false, binding.progressIndicator)
                        showToast(this, result.data.message)
                        val email = result.data.userCredential?.user?.email
                        val token = result.data.userCredential?.user?.stsTokenManager?.accessToken
                        if (email != null && token != null) {
                            viewModel.saveSession(email, true, token)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }

                    is Result.Error -> {
                        showLoading(false, binding.progressIndicator)
                        showToast(this, result.error)

                    }
                }
        }
    }
}