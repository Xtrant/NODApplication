package com.dicoding.nodapplication.data.response

import com.google.gson.annotations.SerializedName

data class ListDestinationResponseItem(

	@field:SerializedName("Reviews")
	val reviews: String? = null,

	@field:SerializedName("Nama Wisata")
	val namaWisata: String? = null,

	@field:SerializedName("Jenis Wisata")
	val jenisWisata: String? = null,

	@field:SerializedName("Rating")
	val rating: Any? = null,

	@field:SerializedName("User_Distance")
	val userDistance: String? = null,

	@field:SerializedName("Kabupaten/Kota")
	val kabupatenKota: String? = null
)
