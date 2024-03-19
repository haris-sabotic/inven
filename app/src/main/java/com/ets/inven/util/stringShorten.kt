package com.ets.inven.util

fun String.shorten(length: Int): String {
    return if (this.length <= length)
        this
    else
        "${this.substring(0, length-3)}..."
}