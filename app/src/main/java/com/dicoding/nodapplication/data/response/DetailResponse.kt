package com.dicoding.nodapplication.data.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("isNewData")
	val isNewData: Boolean,

	@field:SerializedName("placeData")
	val placeData: PlaceData
)

data class PlaceData(

	@field:SerializedName("types")
	val types: List<String>,

	@field:SerializedName("Nama Wisata")
	val namaWisata: String,

	@field:SerializedName("reviews")
	val reviews: List<ReviewsItem>,

	@field:SerializedName("photos")
	val photos: List<PhotosItem>,

	@field:SerializedName("place_id")
	val placeId: String
)

data class ReviewsItem(

	@field:SerializedName("author_name")
	val authorName: String,

	@field:SerializedName("profile_photo_url")
	val profilePhotoUrl: String,

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("author_url")
	val authorUrl: String,

	@field:SerializedName("rating")
	val rating: Int,

	@field:SerializedName("language")
	val language: String,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("time")
	val time: Int,

	@field:SerializedName("translated")
	val translated: Boolean,

	@field:SerializedName("relative_time_description")
	val relativeTimeDescription: String
)

data class PhotosItem(

	@field:SerializedName("photo_reference")
	val photoReference: String,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<String>,

	@field:SerializedName("height")
	val height: Int
)
