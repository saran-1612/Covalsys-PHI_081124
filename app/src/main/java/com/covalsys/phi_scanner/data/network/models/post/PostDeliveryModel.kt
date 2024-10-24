package com.covalsys.phi_scanner.data.network.models.post

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class PostDeliveryModel(
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("customerCode")
    val customerCode: String,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("docDate")
    val docDate: String,
    @SerializedName("docEntry")
    val docEntry: String,
    @SerializedName("docNum")
    val docNum: String,
    @SerializedName("line")
    val line: List<Line>,
    @SerializedName("loggedUserId")
    val loggedUserId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updatedDate")
    val updatedDate: String
) : Parcelable {
    @Parcelize
    data class Line(
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
        @SerializedName("thick")
        val thick: String,
        @SerializedName("width")
        val width: String
    ) : Parcelable
}