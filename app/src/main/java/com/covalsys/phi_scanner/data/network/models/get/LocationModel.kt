package com.covalsys.phi_scanner.data.network.models.get

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LocationModel(
    @SerializedName("LocationCode")
    val locationCode: String
) : Parcelable