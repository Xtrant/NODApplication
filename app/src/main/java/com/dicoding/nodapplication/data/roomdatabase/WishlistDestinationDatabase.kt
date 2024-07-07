package com.dicoding.nodapplication.data.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.nodapplication.data.entity.WishlistDestination
import kotlin.concurrent.Volatile

@Database(entities = [WishlistDestination::class], version = 8)
abstract class WishlistDestinationDatabase : RoomDatabase() {
    abstract fun wishlistDestinationDao(): WishlistDestinationDao

    companion object {
        @Volatile
        private var INSTANCE: WishlistDestinationDatabase? = null
        fun getInstance(context: Context): WishlistDestinationDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    WishlistDestinationDatabase::class.java, "Wishlist.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }
}