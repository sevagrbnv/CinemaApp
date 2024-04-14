package ru.sevagrbnv.cinemaapp.data.remote.models.detailMain

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("kp") val kp: Double,
)