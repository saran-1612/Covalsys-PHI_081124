package com.covalsys.phi_scanner.data.network.models.get


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetBaseResponsModel(
    @SerializedName("responseObject")
    val responseObject: ResponseObject,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("batch")
        val batch: String,
        @SerializedName("quantity")
        val quantity: Int
    ) : Parcelable
}