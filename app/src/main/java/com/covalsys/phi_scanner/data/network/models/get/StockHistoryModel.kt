package com.covalsys.phi_scanner.data.network.models.get


import com.google.gson.annotations.SerializedName

data class StockHistoryModel(
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) {
    data class ResponseObject(
        @SerializedName("loggedUser")
        val loggedUser: String,
        @SerializedName("rowNum")
        val rowNum: Int,
        @SerializedName("scanBatch")
        val scanBatch: String,
        @SerializedName("scanDate")
        val scanDate: String,
        @SerializedName("scanLocation")
        val scanLocation: String,
        @SerializedName("scanQty")
        val scanQty: Int,
        @SerializedName("scanTime")
        val scanTime: String,
        @SerializedName("type")
        val type: String
    )
}