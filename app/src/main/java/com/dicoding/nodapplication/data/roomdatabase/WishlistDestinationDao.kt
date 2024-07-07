package com.dicoding.nodapplication.data.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.nodapplication.data.entity.WishlistDestination

@Dao
interface WishlistDestinationDao {
    @Query("SELECT * FROM wishlist Order BY destinationName ASC")
    fun getAllWishlistDestination(): LiveData<List<WishlistDestination>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWishlist(wishlist: WishlistDestination)

    @Delete
    suspend fun deleteWishlist(wishlist: WishlistDestination)
}