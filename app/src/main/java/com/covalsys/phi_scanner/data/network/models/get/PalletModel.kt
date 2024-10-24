package com.covalsys.phi_scanner.data.network.models.get

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class PalletModel(
    @SerializedName("PalletCode")
    val palletCode: String
) : Parcelable