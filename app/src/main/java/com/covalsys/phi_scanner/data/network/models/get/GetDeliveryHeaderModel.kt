package com.covalsys.phi_scanner.data.network.models.get


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetDeliveryHeaderModel(
    @SerializedName("responseObject")
    val responseObject: ResponseObject,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("appStatus")
        val appStatus: String,
        @SerializedName("cusCode")
        val cusCode: String,
        @SerializedName("cusName")
        val cusName: String,
        @SerializedName("docEntry")
        val docEntry: String,
        @SerializedName("docNum")
        val docNum: String,
        @SerializedName("docStatus")
        val docStatus: String,
        @SerializedName("docType")
        val docType: String
    ) : Parcelable
}