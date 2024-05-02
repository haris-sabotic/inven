package com.ets.inven.api.requests

data class RegisterRequest(
    val type: String,
    val name: String,
    val email: String,
    val password: String,
)
