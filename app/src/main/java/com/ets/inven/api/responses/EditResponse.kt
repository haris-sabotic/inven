package com.ets.inven.api.responses

import com.ets.inven.models.UserModel

data class EditResponse(
    val message: String,
    val new_user: UserModel
)
