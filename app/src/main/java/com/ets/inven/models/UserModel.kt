package com.ets.inven.models

data class UserModel(
    val type: String,
    val name: String,
    val email: String,
    val phone: String?,
    val about: String?,
    val cv: String?,
    val photo: String,
)
