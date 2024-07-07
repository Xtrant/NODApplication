package com.dicoding.nodapplication.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("userCredential")
    val userCredential: UserCredential? = null
)

data class UserCredential(

    @field:SerializedName("user")
    val user: User? = null
)

data class User(

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("stsTokenManager")
    val stsTokenManager: StsTokenManager
)

data class StsTokenManager(

    @field:SerializedName("accessToken")
    val accessToken: String,

)

