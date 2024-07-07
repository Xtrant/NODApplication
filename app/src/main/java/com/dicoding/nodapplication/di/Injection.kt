package com.dicoding.nodapplication.di

import android.content.Context
import com.dicoding.nodapplication.data.UserRepository
import com.dicoding.nodapplication.data.WishlistRepository
import com.dicoding.nodapplication.data.pref.UserPreferences
import com.dicoding.nodapplication.data.pref.dataStore
import com.dicoding.nodapplication.data.retrofit.ApiConfig
import com.dicoding.nodapplication.data.roomdatabase.WishlistDestinationDatabase
import com.dicoding.nodapplication.view.ViewModelFactory

object Injection {

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val database = WishlistDestinationDatabase.getInstance(context)
        val mWishlistDao = database.wishlistDestinationDao()
        val wishlistRepository = WishlistRepository.getInstance(mWishlistDao)
        val userRepository = UserRepository.getInstance(apiService)
        return ViewModelFactory(userRepository, preferences, context, wishlistRepository)
    }
}