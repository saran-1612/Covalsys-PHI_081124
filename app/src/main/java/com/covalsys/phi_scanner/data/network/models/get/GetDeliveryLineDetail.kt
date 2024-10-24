package com.covalsys.phi_scanner.data.network.models.get


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetDeliveryLineDetail(
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("barCodeBatch")
        val barCodeBatch: String,
        @SerializedName("barCodeQty")
        val barCodeQty: String,
        @SerializedName("batchNo")
        val batchNo: String,
        @SerializedName("docEntry")
        val docEntry: String,
        @SerializedName("itemCode")
        val itemCode: String,
        @SerializedName("itemName")
        val itemName: String,
        @SerializedName("length1")
        val length1: String,
        @SerializedName("length2")
        val length2: String,
        @SerializedName("line")
        val line: String,
        @SerializedName("quantity")
        val quantity: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("CoilNo")
        val coilNo: String,
        @SerializedName("thick")
        val thick: String,
        @SerializedName("width")
        val width: String
    ) : Parcelable
}