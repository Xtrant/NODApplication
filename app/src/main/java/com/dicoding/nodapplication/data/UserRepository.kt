package com.dicoding.nodapplication.data

import com.dicoding.nodapplication.data.response.DetailResponse
import com.dicoding.nodapplication.data.response.ListDestinationResponseItem
import com.dicoding.nodapplication.data.response.LoginResponse
import com.dicoding.nodapplication.data.response.MessageResponse
import com.dicoding.nodapplication.data.retrofit.ApiConfig
import com.dicoding.nodapplication.data.retrofit.ApiService
import com.dicoding.nodapplication.result.Result
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.HttpException

class UserRepository(private val apiService: ApiService) {

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val jsonObject = JsonObject().apply {
                addProperty("email", email)
                addProperty("password", password)
            }

            val response = apiService.login(jsonObject)
            Result.Success(response)

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            val errorMessage = errorBody.error
            Result.Error(errorMessage ?: "Unknown Error")
        }
    }

    suspend fun register(email: String, password: String): Result<MessageResponse> {
        return try {
            val jsonObject = JsonObject().apply {
                addProperty("email", email)
                addProperty("password", password)
            }
            val response = apiService.register(jsonObject)
            Result.Success(response)

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            val errorMessage = errorBody.error
            Result.Error(errorMessage ?: "Unknown Error")
        }
    }

    suspend fun logout(): Result<MessageResponse> {
        return try {
            val response = apiService.logout()
            Result.Success(response)

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            val errorMessage = errorBody.error
            Result.Error(errorMessage ?: "Unknown Error")
        }
    }

    suspend fun listDestination(lat: Double, lon: Double ): Result<List<ListDestinationResponseItem>> {
        return try {
            val jsonObject = JsonObject().apply {
                addProperty("latitude", lat)
                addProperty("longitude", lon)
            }
            val response = apiService.listDestination(jsonObject)
            Result.Success(response)

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            val errorMessage = errorBody.error
            Result.Error(errorMessage ?: "Unknown Error")
        }
    }

    suspend fun listCategoryDestination(category: String, lat: Double, lon: Double ): Result<List<ListDestinationResponseItem>> {
        return try {
            val jsonObject = JsonObject().apply {
                addProperty("latitude", lat)
                addProperty("longitude", lon)
                addProperty("category", category)
            }
            val response = apiService.listCategoryDestination(jsonObject)
            Result.Success(response)

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            val errorMessage = errorBody.error
            Result.Error(errorMessage ?: "Unknown Error")
        }
    }

    suspend fun listReviewDestination(lat: Double, lon: Double ): Result<List<ListDestinationResponseItem>> {
        return try {
            val jsonObject = JsonObject().apply {
                addProperty("latitude", lat)
                addProperty("longitude", lon)
            }
            val response = apiService.listReviewDestination(jsonObject)
            Result.Success(response)

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            val errorMessage = errorBody.error
            Result.Error(errorMessage ?: "Unknown Error")
        }
    }

    suspend fun detailDestination(name: String): Result<DetailResponse> {
        return try {
            val response = apiService.detailDestination(name)
            Result.Success(response)

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            val errorMessage = errorBody.error
            Result.Error(errorMessage ?: "Unknown Error")
        }
    }

    suspend fun ratingDestination(name: String, rating: String, comment: String, token: String): Result<MessageResponse> {
        return try {
            val jsonObject = JsonObject().apply {
                addProperty("rating", rating)
                addProperty("comment", comment)
            }
            val response = ApiConfig.getApiServiceReview().rating("access_token=$token", name, jsonObject)
            Result.Success(response)

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            val errorMessage = errorBody.error
            Result.Error(errorMessage ?: "Unknown Error")
        }
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}