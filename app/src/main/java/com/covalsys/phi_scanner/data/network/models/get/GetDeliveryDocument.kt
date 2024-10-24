package com.covalsys.phi_scanner.data.network.models.get

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetDeliveryDocument(
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("docEntry")
        val docEntry: Int,
        @SerializedName("docNum")
        val docNum: Int
    ) : Parcelable{
        override fun toString(): String {
            return docNum.toString()
        }
    }
}