package com.dicoding.nodapplication.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.nodapplication.data.UserRepository
import com.dicoding.nodapplication.data.WishlistRepository
import com.dicoding.nodapplication.data.pref.UserPreferences
import com.dicoding.nodapplication.di.Injection
import com.dicoding.nodapplication.view.auth.AuthViewModel
import com.dicoding.nodapplication.view.home.DestinationViewModel
import com.dicoding.nodapplication.view.wishlist.WishlistViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences,
    private val context: Context,
    private val wishlistRepository: WishlistRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(userRepository, userPreferences) as T

            modelClass.isAssignableFrom(DestinationViewModel::class.java) ->
                DestinationViewModel(userRepository, context) as T

            modelClass.isAssignableFrom(WishlistViewModel::class.java) ->
                WishlistViewModel(wishlistRepository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                Injection.provideViewModelFactory(context)
            }.also { INSTANCE = it }
    }
}

