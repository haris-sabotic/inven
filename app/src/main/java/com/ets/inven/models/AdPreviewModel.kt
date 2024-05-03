package com.ets.inven.models

import android.os.Parcel
import android.os.Parcelable

data class AdPreviewModel(
    val id: Int,
    val name: String,
    val description: String,
    val photo: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdPreviewModel> {
        override fun createFromParcel(parcel: Parcel): AdPreviewModel {
            return AdPreviewModel(parcel)
        }

        override fun newArray(size: Int): Array<AdPreviewModel?> {
            return arrayOfNulls(size)
        }
    }
}