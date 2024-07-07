package com.dicoding.nodapplication.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity ("wishlist")
class WishlistDestination  {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo
    var destinationName: String = ""
    
    @ColumnInfo
    var destinationRating: String? = null

    @ColumnInfo
    var destinationAmountReview: String? = null

    @ColumnInfo
    var destinationDistance: String? = null

    @ColumnInfo
    var destinationCity: String? = null

    @ColumnInfo
    var destinationType: String? = null

}