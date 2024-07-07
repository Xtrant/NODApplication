package com.dicoding.nodapplication.data.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: String? = null,

)
