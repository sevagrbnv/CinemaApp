package ru.sevagrbnv.cinemaapp.data.remote.models.posters

import com.google.gson.annotations.SerializedName

data class PosterDoc(
    @SerializedName("id") val id: String,
    @SerializedName("previewUrl") val previewUrl: String,
)