package com.dicoding.nodapplication.data.parcelabe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    val amountReview: String,
    val name: String,
    val jenis: String,
    val rating: String,
    val userDistance: String,
    val kota: String,
): Parcelable
