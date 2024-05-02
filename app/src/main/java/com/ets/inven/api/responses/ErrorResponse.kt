package com.ets.inven.api.responses

data class ErrorResponse<T>(
    val message: String,
    val errors: T?
)
