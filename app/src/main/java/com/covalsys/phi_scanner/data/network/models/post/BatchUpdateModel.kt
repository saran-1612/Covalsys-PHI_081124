package com.covalsys.phi_scanner.data.network.models.post

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class BatchUpdateModel(
    @SerializedName("batchNo")
    val batchNo: String,
    @SerializedName("docNum")
    val docNum: String,
    @SerializedName("itemType")
    val itemType: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("loggedUserId")
    val loggedUserId: String,
    @SerializedName("profile")
    val profile: String,
    @SerializedName("quantity")
    val quantity: Float,
    @SerializedName("scanData")
    val scanData: String,
    @SerializedName("scanDate")
    val scanDate: String,
    @SerializedName("scanTime")
    val scanTime: String
) : Parcelable