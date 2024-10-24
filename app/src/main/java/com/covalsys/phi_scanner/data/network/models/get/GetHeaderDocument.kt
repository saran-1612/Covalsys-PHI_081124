package com.covalsys.phi_scanner.data.network.models.get


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetHeaderDocument(
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("CustomerCode")
        val customerCode: String,
        @SerializedName("CustomerName")
        val customerName: String,
        @SerializedName("CustomerType")
        val customerType: String,
        @SerializedName("DocDate")
        val docDate: String,
        @SerializedName("DocEntry")
        val docEntry: Int,
        @SerializedName("DocNum")
        val docNum: Int,
        @SerializedName("DocTotal")
        val docTotal: String,
        @SerializedName("DocType")
        val docType: String,
        @SerializedName("Status")
        val status: String
    ) : Parcelable
}