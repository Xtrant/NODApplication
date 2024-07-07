package com.dicoding.nodapplication.view.home

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.nodapplication.R
import com.dicoding.nodapplication.data.parcelabe.Destination
import com.dicoding.nodapplication.databinding.ActivityRatingBinding
import com.dicoding.nodapplication.result.Result
import com.dicoding.nodapplication.utils.showLoading
import com.dicoding.nodapplication.utils.showToast
import com.dicoding.nodapplication.view.ViewModelFactory
import com.dicoding.nodapplication.view.auth.AuthViewModel

class RatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding
    private var rating = "3"

    private val viewModelDestination by viewModels<DestinationViewModel> {
        ViewModelFactory.getInstance(
            this
        )
    }

    private val viewModelAuth by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(
            this
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setDestination()

        setStarRating()

        binding.materialButton.setOnClickListener {
            sendRating()
        }

        observeViewModel()

        supportActionBar?.hide()
    }

    private fun getDestination(): Destination? {
        val destination = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DetailActivity.DESTINATION, Destination::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DetailActivity.DESTINATION)
        }
        return destination
    }

    private fun setDestination() {
        val destination = getDestination()

        if (destination != null) {
            binding.run {
                tvNameDestination.text = destination.name
                tvRating.text = destination.rating
                amountReview.text = destination.amountReview
                tvContentJenis.text = destination.jenis
                tvContentKota.text = destination.kota
                tvContentJarak.text = destination.userDistance

            }
        }
    }

    private fun sendRating() {
        val destination = getDestination()
        val comment = binding.etDesc.text.toString()
        if (destination != null) {
            if (comment.isNotEmpty()) {
                viewModelAuth.getToken().observe(this) {
                    if (it.isNotEmpty()) {
                        viewModelDestination.postReview(destination.name, comment, rating, it)
                    }

                }
            } else showToast(this, "Please fill in all required fields")
        }
    }

    private fun observeViewModel() {
        viewModelDestination.resultMessage.observe(this) { result ->
            if (result != null)
                when (result) {
                    is Result.Loading -> {
                        showLoading(true, binding.progressIndicator)
                    }

                    is Result.Success -> {
                        showLoading(false, binding.progressIndicator)
                        result.data.message?.let { showToast(this, it) }
                        finish()
                    }


                    is Result.Error -> {
                        showLoading(false, binding.progressIndicator)
                        showToast(this, result.error)

                    }
                }
        }
    }

    private fun setStarRating() {
        binding.star1.setOnClickListener {
            binding.run {
                star1.setImageResource(R.drawable.baseline_star_rate_24)
                star2.setImageResource(R.drawable.baseline_star_outline_24)
                star3.setImageResource(R.drawable.baseline_star_outline_24)
                star4.setImageResource(R.drawable.baseline_star_outline_24)
                star5.setImageResource(R.drawable.baseline_star_outline_24)
                rating = "1"
            }
        }

        binding.star2.setOnClickListener {
            binding.run {
                star1.setImageResource(R.drawable.baseline_star_rate_24)
                star2.setImageResource(R.drawable.baseline_star_rate_24)
                star3.setImageResource(R.drawable.baseline_star_outline_24)
                star4.setImageResource(R.drawable.baseline_star_outline_24)
                star5.setImageResource(R.drawable.baseline_star_outline_24)
                rating = "2"
            }
        }

        binding.star3.setOnClickListener {
            binding.run {
                star1.setImageResource(R.drawable.baseline_star_rate_24)
                star2.setImageResource(R.drawable.baseline_star_rate_24)
                star3.setImageResource(R.drawable.baseline_star_rate_24)
                star4.setImageResource(R.drawable.baseline_star_outline_24)
                star5.setImageResource(R.drawable.baseline_star_outline_24)
                rating = "3"
            }
        }

        binding.star4.setOnClickListener {
            binding.run {
                star1.setImageResource(R.drawable.baseline_star_rate_24)
                star2.setImageResource(R.drawable.baseline_star_rate_24)
                star3.setImageResource(R.drawable.baseline_star_rate_24)
                star4.setImageResource(R.drawable.baseline_star_rate_24)
                star5.setImageResource(R.drawable.baseline_star_outline_24)
                rating = "4"
            }
        }

        binding.star5.setOnClickListener {
            binding.run {
                star1.setImageResource(R.drawable.baseline_star_rate_24)
                star2.setImageResource(R.drawable.baseline_star_rate_24)
                star3.setImageResource(R.drawable.baseline_star_rate_24)
                star4.setImageResource(R.drawable.baseline_star_rate_24)
                star5.setImageResource(R.drawable.baseline_star_rate_24)
                rating = "5"
            }
        }


    }

    companion object {
        const val DESTINATION = "destination"
        const val NAME = "name"
    }
}