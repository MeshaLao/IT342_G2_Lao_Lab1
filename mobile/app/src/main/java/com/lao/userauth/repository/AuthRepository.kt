package com.lao.userauth.repository

import com.lao.userauth.api.RetrofitInstance
import com.lao.userauth.models.*

class AuthRepository {

    private val api = RetrofitInstance.api

    suspend fun register(userName: String, email: String, password: String): AuthResponse {
        val request = RegisterRequest(userName, email, password)
        val response = api.register(request)

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Registration failed: ${response.message()}")
        }
    }

    suspend fun login(email: String, password: String): AuthResponse {
        val request = LoginRequest(email, password)
        val response = api.login(request)

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Login failed: ${response.message()}")
        }
    }

    suspend fun getProfile(token: String): User {
        val response = api.getProfile("Bearer $token")

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Failed to load profile")
        }
    }
}