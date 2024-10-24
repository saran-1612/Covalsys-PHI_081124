package com.covalsys.phi_scanner.data.network.models.get

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginModel(
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("CREATEDATE")
        val createdDate: String,
        @SerializedName("USERID")
        val docEntry: Int,
        @SerializedName("USERCODE")
        val userCode: String,
        @SerializedName("USERTYPE")
        val userType: String,
        @SerializedName("USERNAME")
        val userName: String
    ) : Parcelable
}