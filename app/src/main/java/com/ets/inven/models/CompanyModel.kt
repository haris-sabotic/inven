package com.ets.inven.models

import android.os.Parcel
import android.os.Parcelable

data class CompanyModel(
    val name: String,
    val email: String,
    val phone: String?,
    val about: String?,
    val photo: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(about)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CompanyModel> {
        override fun createFromParcel(parcel: Parcel): CompanyModel {
            return CompanyModel(parcel)
        }

        override fun newArray(size: Int): Array<CompanyModel?> {
            return arrayOfNulls(size)
        }
    }
}