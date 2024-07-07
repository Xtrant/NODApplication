package com.dicoding.nodapplication.data

import androidx.lifecycle.LiveData
import com.dicoding.nodapplication.data.entity.WishlistDestination
import com.dicoding.nodapplication.data.roomdatabase.WishlistDestinationDao

class WishlistRepository(private val mWishlistDao: WishlistDestinationDao) {


    fun getAllUsers(): LiveData<List<WishlistDestination>> = mWishlistDao.getAllWishlistDestination()

    suspend fun delete(wishlist: WishlistDestination){
       mWishlistDao.deleteWishlist(wishlist)
    }

    suspend fun insert(wishlist: WishlistDestination){
       mWishlistDao.insertWishlist(wishlist)
    }

    companion object {
        @Volatile
        private var INSTANCE: WishlistRepository? = null
        fun getInstance(mWishlistDao: WishlistDestinationDao)=
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: WishlistRepository(mWishlistDao)
            }.also { INSTANCE = it }
        }
}