package com.example.storyapp2.data.api

import com.example.storyapp2.data.response.DetailStoriesResponse
import com.example.storyapp2.data.response.GeneralResponse
import com.example.storyapp2.data.response.LoginResponse
import com.example.storyapp2.data.response.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") Name: String,
        @Field("email") Email: String,
        @Field("password") Password: String
    ): Call<GeneralResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") Email: String,
        @Field("password") Password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): StoriesResponse

    @GET("stories")
    fun getAllStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 0
    ): Call<StoriesResponse>

    @GET("stories/{id}")
    fun detailStory(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): Call<DetailStoriesResponse>

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<GeneralResponse>
}