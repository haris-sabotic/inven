package com.ets.inven.api.requests

data class LoginRequest(
    val email: String,
    val password: String,
)
