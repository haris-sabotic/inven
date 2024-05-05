package com.ets.inven.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AdModel(
    val name: String,
    val description: String,

    @SerializedName("positions_left")
    val freePositions: Int,

    val photo: String,
    val company: CompanyModel
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(CompanyModel::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(freePositions)
        parcel.writeString(photo)
        parcel.writeParcelable(company, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdModel> {
        override fun createFromParcel(parcel: Parcel): AdModel {
            return AdModel(parcel)
        }

        override fun newArray(size: Int): Array<AdModel?> {
            return arrayOfNulls(size)
        }
    }
}
