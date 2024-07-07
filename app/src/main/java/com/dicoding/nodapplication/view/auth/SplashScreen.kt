package com.dicoding.nodapplication.view.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.nodapplication.R
import com.dicoding.nodapplication.databinding.ActivitySplashScreenBinding
import com.dicoding.nodapplication.view.ViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        titleColorText()

        binding.btnSplash.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        viewModel.isLogin().observe(this) {
            if (it) {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
                @Suppress("DEPRECATION")
                overridePendingTransition(0, 0)
            }
        }
    }

    private fun titleColorText() {
        val fullText = resources.getString(R.string.title_splash)

        val startIndex = fullText.indexOf("of")
        val endIndex = startIndex + "of".length

        val titleSpannableString = SpannableString(fullText)
        val colorSecondary = ContextCompat.getColor(this, R.color.secondary)
        val colorSpan = ForegroundColorSpan(colorSecondary)
        titleSpannableString.setSpan(
            colorSpan,
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvTitle.text = titleSpannableString
    }
}