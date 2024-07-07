package com.dicoding.nodapplication.data.retrofit

import com.dicoding.nodapplication.data.response.DetailResponse
import com.dicoding.nodapplication.data.response.ListDestinationResponseItem
import com.dicoding.nodapplication.data.response.LoginResponse
import com.dicoding.nodapplication.data.response.MessageResponse
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/register")
    suspend fun register(
        @Body rawJson: JsonObject
    ): MessageResponse

    @POST("api/login")
    suspend fun login (
        @Body rawJson: JsonObject
    ): LoginResponse

    @POST("api/logout")
    suspend fun logout(
    ): MessageResponse

    @POST("search/nearby")
    suspend fun listDestination (
        @Body rawJson: JsonObject
    ): List<ListDestinationResponseItem>

    @POST("search/category")
    suspend fun listCategoryDestination(
        @Body rawJson: JsonObject
    ): List<ListDestinationResponseItem>

    @POST("search/review")
    suspend fun listReviewDestination(
        @Body rawJson: JsonObject
    ): List<ListDestinationResponseItem>

    @GET("search/details/{name}")
    suspend fun detailDestination (
        @Path("name") name: String
    ): DetailResponse

    @POST("reviews/{name}")
    suspend fun rating(
        @Header("Cookie") token: String,
        @Path("name") name: String,
        @Body rawJson: JsonObject,
    ): MessageResponse


}