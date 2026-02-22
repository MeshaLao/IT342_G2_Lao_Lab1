package com.lao.userauth.api

import com.lao.userauth.models.*
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @GET("api/user/me")
    suspend fun getProfile(@Header("Authorization") token: String): Response<User>
}