package com.ets.inven.api

import com.ets.inven.api.requests.EditUserRequest
import com.ets.inven.api.requests.LoginRequest
import com.ets.inven.api.requests.RegisterRequest
import com.ets.inven.api.responses.AppliedResponse
import com.ets.inven.api.responses.EditResponse
import com.ets.inven.api.responses.LoginResponse
import com.ets.inven.api.responses.RegisterResponse
import com.ets.inven.models.AdModel
import com.ets.inven.models.AdPreviewModel
import com.ets.inven.models.UserModel
import com.ets.inven.util.GlobalData
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object Api {
    private val retrofit: Retrofit

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val headersInterceptor = Interceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header("Accept", "application/json")
            chain.proceed(requestBuilder.build())
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(GlobalData.API_PREFIX)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: ApiInterface = retrofit.create()
}

interface ApiInterface {
    @POST("login")
    fun login(@Body body: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun register(@Body body: RegisterRequest): Call<RegisterResponse>

    @GET("user")
    fun user(@Header("Authorization") token: String): Call<UserModel>

    @Multipart
    @POST("user/edit")
    fun editUserPhoto(@Header("Authorization") token: String, @Part photo: MultipartBody.Part): Call<EditResponse>

    @Multipart
    @POST("user/edit")
    fun editUserCV(@Header("Authorization") token: String, @Part cv: MultipartBody.Part): Call<EditResponse>

    @POST("user/edit")
    fun editUser(@Header("Authorization") token: String, @Body body: EditUserRequest): Call<EditResponse>

    @GET("ads")
    fun allAds(@Query("types") type: String?): Call<ArrayList<AdPreviewModel>>

    @GET("ads/{id}")
    fun singleAd(@Path("id") adId: Int): Call<AdModel>

    @GET("user/ads")
    fun userAds(@Header("Authorization") token: String, @Query("types") type: String?): Call<ArrayList<AdPreviewModel>>

    @Multipart
    @POST("user/ads")
    fun createAd(
        @Header("Authorization") token: String,
        @Part("type") type: RequestBody,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("max_available_positions") maxAvailablePositions: RequestBody,
        @Part photo: MultipartBody.Part?
    ): Call<Any>

    @GET("user/ads/{id}/applications")
    fun adApplications(@Header("Authorization") token: String, @Path("id") id: Int): Call<ArrayList<UserModel>>

    @POST("user/ads/{id}/apply")
    fun adApply(@Header("Authorization") token: String, @Path("id") id: Int): Call<Any>

    @GET("user/ads/{id}/applied")
    fun adApplied(@Header("Authorization") token: String, @Path("id") id: Int): Call<AppliedResponse>
}