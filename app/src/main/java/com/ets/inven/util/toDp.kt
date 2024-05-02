package com.ets.inven.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun Float.toDp(context: Context): Int {
    val r: Resources = context.resources

    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        r.displayMetrics
    ).toInt()
}