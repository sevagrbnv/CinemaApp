package ru.sevagrbnv.cinemaapp.data.remote.models.detailMain

import com.google.gson.annotations.SerializedName

data class PosterDto(
    @SerializedName("previewUrl") val previewUrl: String
)