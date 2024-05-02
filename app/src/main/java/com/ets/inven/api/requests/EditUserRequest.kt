package com.ets.inven.api.requests

data class EditUserRequest(
    val name: String?,
    val email: String?,
    val phone: String?,
    val about: String?,
)
