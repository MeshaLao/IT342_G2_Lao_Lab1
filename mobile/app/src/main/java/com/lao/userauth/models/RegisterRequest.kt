package com.lao.userauth.models

data class RegisterRequest(
    val userName: String,
    val email: String,
    val password: String
)