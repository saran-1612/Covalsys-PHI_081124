package com.covalsys.phi_scanner.data.network.models.get


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class DeliveryLineModel(
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("BatchNo")
        val batchNo: String,
        @SerializedName("BatchStatus")
        val batchStatus: String,
        @SerializedName("DocEntry")
        val docEntry: String,
        @SerializedName("DocNum")
        val docNum: Int,
        @SerializedName("ItemCode")
        val itemCode: String,
        @SerializedName("ItemName")
        val itemName: String,
        @SerializedName("Line")
        val line: Int,
        @SerializedName("Pcs")
        val pcs: String,
        @SerializedName("Quantity")
        val quantity: String,
        @SerializedName("Uom")
        val uom: String
    ) : Parcelable
}