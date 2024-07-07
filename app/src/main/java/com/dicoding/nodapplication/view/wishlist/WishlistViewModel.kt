package com.dicoding.nodapplication.view.wishlist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nodapplication.data.WishlistRepository
import com.dicoding.nodapplication.data.entity.WishlistDestination
import com.dicoding.nodapplication.utils.showToast
import kotlinx.coroutines.launch

class WishlistViewModel(private val wishlistRepository: WishlistRepository): ViewModel() {

    private val _isWishlist = MutableLiveData<Boolean>()
    val isWishlist: LiveData<Boolean> = _isWishlist

    init {
        getAllWishlist()
    }

    fun getAllWishlist(): LiveData<List<WishlistDestination>> {
        return wishlistRepository.getAllUsers()
    }

    private fun removeWishlist(wishlist: WishlistDestination) {
        isWishlistDestination(false)
        viewModelScope.launch {
            wishlistRepository.delete(wishlist)
            getAllWishlist()
        }
    }

    private fun addWishlist(wishlist: WishlistDestination) {
        viewModelScope.launch {
            isWishlistDestination(true)
            wishlistRepository.insert(wishlist)
        }
    }

    fun isWishlistDestination(isWishlist: Boolean) {
        _isWishlist.value = isWishlist
    }

    fun updateWishlist(wishlist: WishlistDestination, context: Context) {
        if(isWishlist.value != true){
            addWishlist(wishlist)
            showToast(context, "Added to Wishlist" )
        } else {
            removeWishlist(wishlist)
            showToast(context, "Removed from Wishlist")
        }

    }

}