package com.covalsys.phi_scanner.data.network.models.get


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetChildDocument(
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
        @SerializedName("DocNumber")
        val docNumber: Int,
        @SerializedName("ItemCode")
        val itemCode: String,
        @SerializedName("ItemName")
        val itemName: String,
        @SerializedName("Pcs")
        val pcs: String,
        @SerializedName("Quantity")
        val quantity: String,
        @SerializedName("Uom")
        val uom: String
    ) : Parcelable
}