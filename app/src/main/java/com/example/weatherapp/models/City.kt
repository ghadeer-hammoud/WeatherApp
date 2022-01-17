package com.example.weatherapp.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class City() : Parcelable {

    @SerializedName("name")
    var name: String? = null
    @SerializedName("country")
    var country: String? = null
    @SerializedName("latitude")
    var latitude: Double = 0.0
    @SerializedName("longitude")
    var longitude: Double = 0.0

    var isFavorite: Int = 0

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        country = parcel.readString()
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
        isFavorite = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeInt(isFavorite)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }

}