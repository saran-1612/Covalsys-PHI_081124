package com.covalsys.phi_scanner.data.network.models.get

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetProfileModel(
    @SerializedName("responseObject")
    val responseObject: ResponseObject,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("docNum")
        val docNum: String,
        @SerializedName("openQty")
        val openQty: Int,
        @SerializedName("closedQty")
        val closedQty: Int,
        @SerializedName("profileName")
        val profileName: String
    ) : Parcelable
}

/*
{
    "statusCode": 0,
    "statusMessage": "Success",
    "responseObject": {
    "docNum": "1",
    "openQty": 6077,
    "closedQty": 4,
    "profileName": "JULY 2024 1"
}
}*/
