package com.ets.inven.api.responses

import com.ets.inven.models.UserModel

data class LoginResponse(
    val token: String,
    val user: UserModel
)
