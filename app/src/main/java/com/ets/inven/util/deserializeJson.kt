package com.ets.inven.util

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

inline fun <reified T> deserializeJson(json: String): T {
    val gson = GsonBuilder()
    val type: Type = object : TypeToken<T?>() {}.type
    val result: T = gson.create().fromJson(json, type)

    return result
}