package com.dicoding.nodapplication.view.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nodapplication.R
import com.dicoding.nodapplication.data.adapter.ReviewAdapter
import com.dicoding.nodapplication.data.entity.WishlistDestination
import com.dicoding.nodapplication.data.parcelabe.Destination
import com.dicoding.nodapplication.data.response.ReviewsItem
import com.dicoding.nodapplication.databinding.ActivityDetailBinding
import com.dicoding.nodapplication.result.Result
import com.dicoding.nodapplication.utils.showLoading
import com.dicoding.nodapplication.utils.showToast
import com.dicoding.nodapplication.view.ViewModelFactory
import com.dicoding.nodapplication.view.wishlist.WishlistViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModelDestination by viewModels<DestinationViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModelWishlist by viewModels<WishlistViewModel> {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val destination = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DESTINATION, Destination::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DESTINATION)
        }

        observeViewModel()

        setupRecyclerView()

        destinationReview()

        binding.btnBeriUlasan.setOnClickListener {

            if (destination != null) {
                val intent = Intent(this, RatingActivity::class.java)
                intent.putExtra(RatingActivity.DESTINATION, destination)
                intent.putExtra(RatingActivity.NAME, destination.name)
                startActivity(intent)
            }
        }

        //wishlist
        var wishDestination: WishlistDestination = WishlistDestination().apply {
            if (destination != null) {
                this.destinationName = destination.name
                this.destinationRating = destination.rating
                this.destinationDistance = destination.rating
                this.destinationAmountReview = destination.amountReview
                this.destinationCity = destination.kota
                this.destinationType = destination.jenis
            }
        }

        viewModelWishlist.isWishlist.observe(this) {
            isWishlist(it)
        }

        viewModelWishlist.getAllWishlist().observe(this){
            it.forEach { wish ->
                if (wish.destinationName == destination?.name) {
                    viewModelWishlist.isWishlistDestination(true)
                    wishDestination = wish
                }
            }
        }

        binding.fabFavorite.setOnClickListener {
            wishDestination.let { viewModelWishlist.updateWishlist(it, this) }
        }
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        setDestination()
    }

    override fun onStart() {
        super.onStart()
        showLoading(false, binding.progressIndicator)
    }

    private fun isWishlist(isWish: Boolean) {
        if (isWish) {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
        }
        else binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
    }

    private fun destinationReview() {
        val name = intent.getStringExtra(NAME)
        if (name != null) {
            viewModelDestination.getReviewsItem(name)
        }
    }

    private fun setReviewDestination(review: List<ReviewsItem>) {
        val adapter = ReviewAdapter()
        adapter.submitList(review)
        binding.rvUlasan.adapter = adapter

    }

    private fun setupRecyclerView() {
            val layoutManager = LinearLayoutManager(this)
            binding.rvUlasan.layoutManager = layoutManager
    }

    private fun observeViewModel() {
        viewModelDestination.resultDetailDestination.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true, binding.progressIndicator)

                is Result.Success -> {
                    showLoading(false, binding.progressIndicator)
                    setReviewDestination(result.data.data.placeData.reviews)
                }

                is Result.Error -> {
                    showLoading(false, binding.progressIndicator)
                    showToast(this, result.error)
                }
            }
        }
    }

    private fun setDestination() {
        val destination = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DESTINATION, Destination::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DESTINATION)
        }
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

    companion object {
        const val DESTINATION = "destination"
        const val NAME = "name"
    }
}