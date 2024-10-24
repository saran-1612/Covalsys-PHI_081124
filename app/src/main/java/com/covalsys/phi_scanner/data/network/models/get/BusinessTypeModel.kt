package com.covalsys.phi_scanner.data.network.models.get

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class BusinessTypeModel(
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("businessCode")
        val businessCode: String,
        @SerializedName("businessName")
        val businessName: String
    ) : Parcelable{
        override fun toString(): String {
            return businessName
        }
    }
}