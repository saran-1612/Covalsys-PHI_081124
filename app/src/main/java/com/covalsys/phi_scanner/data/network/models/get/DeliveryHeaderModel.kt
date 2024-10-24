package com.covalsys.phi_scanner.data.network.models.get

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class DeliveryHeaderModel(
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("AreaWork")
        val areaWork: String,
        @SerializedName("CustomerType")
        val customerType: String,
        @SerializedName("DcValidation")
        val dcValidation: String,
        @SerializedName("DocDate")
        val docDate: String,
        @SerializedName("DocEntry")
        val docEntry: String,
        @SerializedName("DocNum")
        val docNum: Int,
        @SerializedName("DocTotal")
        val docTotal: String,
        @SerializedName("EwayBillNo")
        val ewayBillNo: String,
        @SerializedName("LoggedUserId")
        val loggedUserId: String,
        @SerializedName("NoOfDoc")
        val noOfDoc: Int,
        @SerializedName("OutPassNo")
        val outPassNo: String,
        @SerializedName("OutpassDate")
        val outpassDate: String,
        @SerializedName("Priority")
        val priority: String,
        @SerializedName("Remarks")
        val remarks: String,
        @SerializedName("SecurityCode")
        val securityCode: String,
        @SerializedName("SecurityName")
        val securityName: String,
        @SerializedName("Status")
        val status: String,
        @SerializedName("Type")
        val type: String,
        @SerializedName("VehicleNo")
        val vehicleNo: String
    ) : Parcelable
}