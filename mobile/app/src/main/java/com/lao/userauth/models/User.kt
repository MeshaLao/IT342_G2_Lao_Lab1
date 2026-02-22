package com.lao.userauth.models

data class User(
    val userID: Long,
    val userName: String,
    val email: String,
    val createdAt: String
)